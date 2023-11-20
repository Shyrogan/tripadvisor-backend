package fr.samyseb.hotel;

import fr.samyseb.common.JavalinConfigs;
import io.javalin.Javalin;

public class Application {

    public static void main(String[] args) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", ctx -> ctx.json())
                .start(7070);
    }

}
