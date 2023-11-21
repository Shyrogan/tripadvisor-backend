package fr.samyseb.hotel;

import fr.samyseb.common.JavalinConfigs;
import fr.samyseb.common.pojo.Hotel;
import fr.samyseb.hotel.controller.ChambreController;
import fr.samyseb.hotel.controller.HotelController;
import fr.samyseb.hotel.controller.ReservationController;
import io.javalin.Javalin;
import org.jdbi.v3.core.Jdbi;
import picocli.CommandLine;

import static fr.samyseb.common.Jdbis.jdbiPostgre;

public class Application {

    private static Application application;
    private final Jdbi jdbi;
    private final Hotel hotel;

    Application(Hotel hotel, String dbUrl, String dbUser, String dbPassword) {
        application = this;

        this.jdbi = jdbiPostgre(dbUrl, dbUser, dbPassword);
        this.hotel = hotel;
        this.insertHotel(jdbi);
    }

    public static void main(String[] args) {
        new CommandLine(new Command()).execute(args);
    }

    public static Application application() {
        return application;
    }

    public Jdbi jdbi() {
        return jdbi;
    }

    public Hotel hotel() {
        return hotel;
    }

    public void start(int port) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", HotelController::getHotel)

                .get("/chambre", ChambreController::listChambre)
                .put("/chambre", ChambreController::createChambre)
                .patch("/chambre", ChambreController::updateChambre)
                .delete("/chambre", ChambreController::deleteChambre)

                .get("/reservation", ReservationController::listReservations)
                .get("/reservation/{id}", ReservationController::listChambreReservations)

                .start(port);
    }

    private void insertHotel(Jdbi jdbi) {
        jdbi.open()
                .execute("INSERT INTO adresse VALUES (?, ?, ?, ?, ?)",
                        hotel.adresse().id(), hotel.adresse().numero(), hotel.adresse().rue(),
                        hotel.adresse().ville(), hotel.adresse().pays());
        jdbi.open()
                .execute("INSERT INTO hotel VALUES (?, ?, ?, ?, ?)",
                        hotel.id(), hotel.adresse().id(), hotel.nom(), hotel.etoiles(), hotel.url());
    }

}
