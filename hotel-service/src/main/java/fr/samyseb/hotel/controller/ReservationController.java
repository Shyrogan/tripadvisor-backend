package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Chambre;
import fr.samyseb.common.pojo.Reservation;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

import static fr.samyseb.hotel.Application.application;

public class ReservationController {

    public static void listReservations(Context ctx) {
        ctx.json(application().jdbi().open()
                .createQuery("""
                        SELECT r.*, c.nom, c.prenom, cb.* FROM reservation r
                        INNER JOIN client c ON r.id_client = c.id_client
                        INNER JOIN carte_bancaire cb ON c.carte = cb.numero
                        WHERE id_hotel = :id
                        """)
                .bind("id", application().hotel().id())
                .mapTo(Reservation.class)
                .list());
    }

    public static void listChambreReservations(Context ctx) {
        var numeroChambre = ctx.pathParamAsClass("numeroChambre", Integer.class);
        ctx.json(application().jdbi().open()
                .createQuery("""
                        SELECT r.*, c.nom, c.prenom, cb.* FROM reservation r
                        INNER JOIN client c ON r.id_client = c.id_client
                        INNER JOIN carte_bancaire cb ON c.carte = cb.numero
                        WHERE id_hotel = :id AND numero_chambre = :num
                        """)
                .bind("id", application().hotel().id())
                .bind("num", numeroChambre)
                .mapTo(Reservation.class)
                .list());
    }


    public static void createReservation(Context ctx) {
        var reservation = ctx.bodyValidator(Reservation.class)
                .check(r -> r.hotel() == null, "Il n'est pas possible de définir l'ID de l'hôtel d'une réservation à la création")
                .check(r -> r.numeroChambre() > 0 && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_hotel = :hotel AND numero_chambre = :num")
                        .bind("hotel", application().hotel().id())
                        .bind("num", r.numeroChambre())
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isPresent(), "Le numéro de la chambre indiqué n'existe pas")
                .check(r -> {
                    var chevauchement = application().jdbi().open()
                            .createQuery("SELECT COUNT(*) FROM reservation WHERE numero_chambre = :num AND id_hotel = :hotel AND (debut < :nouvelleFin AND fin > :nouveauDebut)")
                            .bind("num", r.numeroChambre())
                            .bind("hotel", application().hotel().id())
                            .bind("nouveauDebut", r.debut())
                            .bind("nouvelleFin", r.fin())
                            .mapTo(Long.class)
                            .one();
                    return chevauchement == 0;
                }, "Les dates de réservation sont en conflit avec une autre réservation existante.")
                .get();

        UUID id = UUID.randomUUID();

        application().jdbi().open()
                .execute("INSERT INTO reservation (id_reservation, id_hotel, numero_chambre, id_client, debut, fin) VALUES (?, ?, ?, ?, ?, ?)",
                        id, application().hotel().id(), reservation.numeroChambre(), reservation.client().id(), reservation.debut(), reservation.fin());

        ctx.json(id).status(HttpStatus.CREATED);
    }


    public static void updateReservation(Context ctx) {
        var reservation = ctx.bodyValidator(Reservation.class)
                .check(r -> r.id() != null && application().jdbi().open()
                        .createQuery("SELECT * FROM reservation WHERE id_reservation = :id")
                        .bind("id", r.id())
                        .mapTo(Reservation.class)
                        .findFirst()
                        .isPresent(), "L'ID de la réservation indiqué n'existe pas")
                .check(r -> r.numeroChambre() > 0 && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_hotel = :hotel AND numero_chambre = :num")
                        .bind("hotel", application().hotel().id())
                        .bind("num", r.numeroChambre())
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isPresent(), "Le numéro de la chambre indiqué n'existe pas")
                .get();

        application().jdbi().open()
                .execute("UPDATE reservation SET numero_chambre = ?, id_client = ?, debut = ?, fin = ? WHERE id_reservation = ?",
                        reservation.numeroChambre(), reservation.client().id(), reservation.debut(), reservation.fin(), reservation.id());

        ctx.status(HttpStatus.OK);
    }

    public static void deleteReservation(Context ctx) {
        var idReservation = ctx.bodyValidator(UUID.class)
                .check(id -> id != null && application().jdbi().open()
                        .createQuery("SELECT * FROM reservation WHERE id_reservation = :id")
                        .bind("id", id)
                        .mapTo(Reservation.class)
                        .findFirst()
                        .isPresent(), "L'ID de la réservation indiqué n'existe pas")
                .get();

        application().jdbi().open()
                .execute("DELETE FROM reservation WHERE id_reservation = ?", idReservation);

        ctx.status(HttpStatus.OK);
    }


}

