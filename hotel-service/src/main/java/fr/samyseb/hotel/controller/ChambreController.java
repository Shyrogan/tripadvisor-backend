package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Chambre;
import fr.samyseb.hotel.Application;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

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
                .check(c -> c.id() == null, "Il n'est pas possible de définir l'ID d'une chambre à la création")
                .check(c -> c.hotel() == null, "Il n'est pas possible de définir l'ID de l'hôtel à la création")
                .check(c -> c.places() >= 1 && c.places() <= 20, "Le nombre de place doit-être compris entre 1..20")
                .check(c -> c.prix() >= 0, "Le prix doit-être une valeur positive")
                .get();
        application().jdbi().open()
                        .execute("INSERT INTO chambre VALUES (?, ?, ?, ?)",
                                UUID.randomUUID(), application().hotel().id(), ch.places(), ch.prix());
        ctx.status(HttpStatus.CREATED);
    }

    public static void updateChambre(Context ctx) {
        var ch = ctx.bodyValidator(Chambre.class)
                .check(c -> c.id() != null && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_chambre = ?")
                        .bind(1, c.id())
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isEmpty(), "L'ID de la chambre indiqué n'existe pas")
                .check(c -> c.hotel() == null, "Il n'est pas possible de définir l'ID de l'hôtel à la modification")
                .check(c -> c.places() >= 1 && c.places() <= 20, "Le nombre de place doit-être compris entre 1..20")
                .check(c -> c.prix() >= 0, "Le prix doit-être une valeur positive")
                .get();
        application().jdbi().open()
                .execute("UPDATE chambre SET places = ?, prix = ? WHERE id_chambre = ?",
                        ch.prix(), ch.places(), ch.id());

        ctx.status(HttpStatus.OK);
    }

    public static void deleteChambre(Context ctx) {
        var id = ctx.bodyValidator(UUID.class)
                .check(uid -> uid != null && application().jdbi().open()
                        .createQuery("SELECT * FROM chambre WHERE id_chambre = ?")
                        .bind(1, uid)
                        .mapTo(Chambre.class)
                        .findFirst()
                        .isEmpty(), "L'ID de la chambre indiqué n'existe pas")
                .get();
        application().jdbi().open()
                .execute("DELETE FROM chambre WHERE id_chambre = ?", id);
    }

}
