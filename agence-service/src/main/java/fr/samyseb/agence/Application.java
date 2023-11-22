package fr.samyseb.agence;

import fr.samyseb.agence.controller.AgenceController;
import fr.samyseb.common.JavalinConfigs;
import fr.samyseb.common.pojo.Agence;
import io.javalin.Javalin;
import org.jdbi.v3.core.Jdbi;
import picocli.CommandLine;

import static fr.samyseb.common.Jdbis.jdbiPostgre;

public class Application {

    private static Application application;
    private final Jdbi jdbi;
    private final Agence agence;

    Application(Agence agence, String jdbcUrl, String pgUser, String pgPassword) {
        application = this;

        this.jdbi = jdbiPostgre(jdbcUrl, pgUser, pgPassword);
        this.agence = agence;
        this.insertAgence(jdbi);
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

    public Agence agence() {
        return agence;
    }

    public void start(int port) {
        Javalin.create(JavalinConfigs::commonConfiguration)
                .get("/", AgenceController::getAgence)
                .start(port);
    }

    private void insertAgence(Jdbi jdbi) {
        jdbi.open().execute("INSERT INTO agence VALUES (?, ?, ?, ?)",
                agence.id(), agence.nom(), agence.password(), agence.url());
    }

}
