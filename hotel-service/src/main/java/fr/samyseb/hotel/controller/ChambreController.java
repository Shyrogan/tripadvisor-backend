package fr.samyseb.hotel.controller;

import fr.samyseb.hotel.Application;
import io.javalin.http.Context;

import static fr.samyseb.hotel.Application.application;

public class ChambreController {

    public static void listChambre(Context ctx) {
        ctx.json(application().jdbi().open()
                .execute("SELECT * FROM chambre"))
    }

    public static void createChambre(Context ctx) {

    }

    public static void updateChambre(Context ctx) {

    }

    public static void deleteChambre(Context ctx) {

    }

}
