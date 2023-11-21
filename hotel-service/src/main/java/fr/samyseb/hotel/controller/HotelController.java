package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Hotel;
import io.javalin.http.Handler;

public class HotelController {

    private Hotel hotel;

    public HotelController(Hotel hotel) {
        this.hotel = hotel;
    }

    public final Handler getHotel = ctx -> {
        ctx.json(hotel);
    };

}
