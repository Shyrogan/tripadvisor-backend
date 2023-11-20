package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Hotel;
import io.javalin.http.Handler;

import static fr.samyseb.hotel.Application.application;

public class HotelController {

    public static final Handler GET_HOTEL = ctx -> {
        ctx.json(application().jdbi().open()
                .createQuery("SELECT * FROM hotel INNER JOIN adresse ON hotel.adresse = adresse.id_adresse")
                .mapTo(Hotel.class)
                .findFirst()
                .orElse(null));
    };

}
