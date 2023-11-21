package fr.samyseb.hotel;

import fr.samyseb.common.JavalinConfigs;
import fr.samyseb.common.pojo.Hotel;
import fr.samyseb.hotel.controller.HotelController;
import io.javalin.Javalin;
import org.jdbi.v3.core.Jdbi;
import picocli.CommandLine;

import static fr.samyseb.common.Jdbis.jdbiPostgre;

public class Application {

    public static void main(String[] args) {
        new CommandLine(new Command()).execute(args);
    }

    private static Application application;

    private final Jdbi jdbi;
    private final HotelController hotelController;

    Application(Hotel hotel) {
        application = this;

        this.jdbi = jdbiPostgre("localhost", "rest", "sebastien", "");
        this.hotelController = new HotelController(hotel);
    }

    public static Application application() {
        return application;
    }

    public Jdbi jdbi() {
        return jdbi;
    }

    public void start(int port) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", hotelController.getHotel)
                .start(port);
    }

}
