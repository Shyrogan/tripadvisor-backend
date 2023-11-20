package fr.samyseb.hotel;

import fr.samyseb.common.JavalinConfigs;
import fr.samyseb.common.Jdbis;
import fr.samyseb.common.pojo.Hotel;
import fr.samyseb.hotel.controller.HotelController;
import io.javalin.Javalin;
import org.jdbi.v3.core.Jdbi;

import static fr.samyseb.common.Jdbis.jdbiPostgre;
import static fr.samyseb.hotel.controller.HotelController.GET_HOTEL;

public class Application {

    public static void main(String[] args) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", GET_HOTEL)
                .start(7070);
    }

    private static final Application application = new Application();

    private final Jdbi jdbi;

    Application() {
        this.jdbi = jdbiPostgre("localhost", "rest", "sebastien", "");
    }

    public static Application application() {
        return application;
    }

    public Jdbi jdbi() {
        return jdbi;
    }
}
