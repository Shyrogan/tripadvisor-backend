package fr.samyseb.hotel.controller;

import io.javalin.http.Context;

import static fr.samyseb.hotel.Application.application;

public class HotelController {

    public static void getHotel(Context ctx) {
        ctx.json(application().hotel());
    }

}
