package fr.samyseb.agence;

import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin.create(/*config*/)
                .get("/", ctx -> ctx.result("Hello World"))
                .start(7070);
    }

}
