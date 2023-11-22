package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Chambre;
import io.javalin.http.Context;

import java.time.LocalDate;
import java.util.List;

import static fr.samyseb.hotel.Application.application;

public class OffreController {

    public static void genererOffres(Context ctx) {
        int nombreDePersonnes = ctx.queryParamAsClass("nombre", Integer.class).getOrDefault(1);
        LocalDate debutSejour = ctx.queryParamAsClass("debut", LocalDate.class).get();
        LocalDate finSejour = ctx.queryParamAsClass("fin", LocalDate.class).get();

        List<Chambre> offresDisponibles = application().jdbi().open()
                .createQuery("""
                        SELECT * FROM chambre WHERE id_hotel = :hotel
                        AND places >= :nombre
                        AND id_chambre NOT IN (
                            SELECT id_chambre FROM reservation
                            WHERE debut <= :fin AND fin >= :debut
                        )
                        """)
                .bind("hotel", application().hotel().id())
                .bind("nombre", nombreDePersonnes)
                .bind("debut", debutSejour)
                .bind("fin", finSejour)
                .mapTo(Chambre.class)
                .list();

        ctx.json(offresDisponibles);
    }
}


