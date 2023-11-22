package fr.samyseb.agence.controller;

import io.javalin.http.Context;

import static fr.samyseb.agence.Application.application;

public class AgenceController {

    public static void getAgence(Context ctx) {
        ctx.json(application().agence());
    }

}
