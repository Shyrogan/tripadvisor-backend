package fr.samyseb.hotel;

import fr.samyseb.common.JavalinConfigs;
import fr.samyseb.common.pojo.Hotel;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", ctx -> ctx.json(ctx.bodyAsClass(Hotel.class)))
                .start(7070);
    }

}
