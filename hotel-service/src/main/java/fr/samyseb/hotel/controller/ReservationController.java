package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Chambre;
import fr.samyseb.common.pojo.Reservation;
import io.javalin.http.Context;

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
        var id = ctx.pathParamAsClass("id", UUID.class);
        ctx.json(application().jdbi().open()
                .createQuery("""
                        SELECT r.*, c.nom, c.prenom, cb.* FROM reservation r
                        INNER JOIN client c ON r.id_client = c.id_client
                        INNER JOIN carte_bancaire cb ON c.carte = cb.numero
                        WHERE id_hotel = :id AND id_chambre = :chambre
                        """)
                .bind("id", application().hotel().id())
                .bind("chambre", id)
                .mapTo(Reservation.class)
                .list());
    }

    public static void createReservation(Context ctx) {
        ctx.bodyValidator(Reservation.class)
                .check(r -> r.id() == null, "Il n'est pas possible de définir l'ID d'une réservation à la création")
                .check(r -> r.hotel() == null, "Il n'est pas possible de définir l'ID de l'hôtel d'une réservation à la création")
                .check(r -> r.chambre() != null && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_chambre = :id AND id_hotel = :hotel")
                        .bind("id", r.chambre())
                        .bind("hotel", application().hotel().id())
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isPresent(), "L'ID de la chambre indiqué n'existe pas");

        // TODO insertion de reservation
    }

}
