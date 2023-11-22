package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Chambre;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

import static fr.samyseb.hotel.Application.application;

public class ChambreController {

    public static void listChambre(Context ctx) {
        ctx.json(application().jdbi().open()
                .createQuery("SELECT * FROM chambre WHERE id_hotel = :id")
                .bind("id", application().hotel().id())
                .mapTo(Chambre.class)
                .list());
    }

    public static void createChambre(Context ctx) {
        var ch = ctx.bodyValidator(Chambre.class)
                .check(c -> c.hotel() == null, "Il n'est pas possible de définir l'ID de l'hôtel à la création")
                .check(c -> c.numeroChambre() > 0, "Un numéro de chambre valide est requis")
                .check(c -> c.places() >= 1 && c.places() <= 20, "Le nombre de place doit-être compris entre 1 et20")
                .check(c -> c.prix() >= 0, "Le prix doit-être une valeur positive")
                .get();
        application().jdbi().open()
                .execute("INSERT INTO chambre VALUES (?, ?, ?, ?)",
                        application().hotel().id(), ch.numeroChambre(), ch.places(), ch.prix());
        var response = Map.of(
                "id_hotel", application().hotel().id(),
                "numero_chambre", ch.numeroChambre()
        );
        ctx.json(response).status(HttpStatus.CREATED);
    }


    public static void updateChambre(Context ctx) {
        var ch = ctx.bodyValidator(Chambre.class)
                .check(c -> c.numeroChambre() > 0 && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_hotel = :hotel AND numero_chambre = :num")
                        .bind("hotel", application().hotel().id())
                        .bind("num", c.numeroChambre())
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isPresent(), "Le numéro de la chambre indiqué n'existe pas")
                .check(c -> c.places() >= 1 && c.places() <= 20, "Le nombre de place doit être compris entre 1..20")
                .check(c -> c.prix() >= 0, "Le prix doit être une valeur positive")
                .get();
        application().jdbi().open()
                .execute("UPDATE chambre SET places = ?, prix = ? WHERE id_hotel = ? AND numero_chambre = ?",
                        ch.places(), ch.prix(), application().hotel().id(), ch.numeroChambre());

        ctx.status(HttpStatus.OK);
    }


    public static void deleteChambre(Context ctx) {
        var numeroChambre = ctx.bodyValidator(Integer.class)
                .check(num -> num > 0 && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_hotel = :hotel AND numero_chambre = :num")
                        .bind("hotel", application().hotel().id())
                        .bind("num", num)
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isPresent(), "Le numéro de la chambre indiqué n'existe pas")
                .get();
        application().jdbi().open()
                .execute("DELETE FROM chambre WHERE id_hotel = ? AND numero_chambre = ?", application().hotel().id(), numeroChambre);

        ctx.status(HttpStatus.OK);
    }

}
