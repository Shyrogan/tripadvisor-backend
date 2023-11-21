package fr.samyseb.hotel;

import fr.samyseb.common.pojo.Adresse;
import fr.samyseb.common.pojo.Hotel;
import picocli.CommandLine;

import java.util.UUID;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "hotel-service", description = "Démarre le service d'un hôtel.")
public class Command implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Le nom de l'hôtel")
    private String nom;
    @CommandLine.Parameters(index = "1", description = "Le nom de l'hôtel")
    private int etoiles;
    @CommandLine.Parameters(index = "2", description = "Le numéro de l'adresse de l'hôtel")
    private String numero;
    @CommandLine.Parameters(index = "3", description = "La rue de l'adresse de l'hôtel")
    private String rue;
    @CommandLine.Parameters(index = "4", description = "La ville de l'adresse de l'hôtel")
    private String ville;
    @CommandLine.Parameters(index = "5", description = "Le pays de l'adresse de l'hôtel")
    private String pays;

    @CommandLine.Option(names = "dbUrl", defaultValue = "jdbc:postgresql://localhost/postgres")
    private String dbUrl;
    @CommandLine.Option(names = "dbUser", defaultValue = "postgres")
    private String dbUser;
    @CommandLine.Option(names = "dbPassword", defaultValue = "")
    private String dbPassword;

    @Override
    public Integer call() {
        new Application(new Hotel(
                UUID.randomUUID(), nom, etoiles,
                new Adresse(UUID.randomUUID(), numero, rue, ville, pays),
                "http://%s:%d".formatted("127.0.0.1", 3000)),
                dbUrl, dbUser, dbPassword
        ).start(3000);

        return 0;
    }
}
