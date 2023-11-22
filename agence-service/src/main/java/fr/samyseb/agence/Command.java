package fr.samyseb.agence;

import fr.samyseb.common.pojo.Agence;
import picocli.CommandLine;

import java.util.UUID;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "hotel-service", description = "Démarre le service d'un hôtel.")
public class Command implements Callable<Integer> {

    @CommandLine.Parameters(index = "0", description = "Le nom de l'agence")
    private String nom;
    @CommandLine.Parameters(index = "0", description = "Le mot de passe de l'agence")
    private String password;

    @CommandLine.Option(names = "dbUrl", defaultValue = "jdbc:postgresql://localhost/postgres")
    private String dbUrl;
    @CommandLine.Option(names = "dbUser", defaultValue = "postgres")
    private String dbUser;
    @CommandLine.Option(names = "dbPassword", defaultValue = "")
    private String dbPassword;

    @Override
    public Integer call() {
        new Application(new Agence(
                UUID.randomUUID(), nom, password,
                "http://%s:%d".formatted("localhost", 4000)
        ), dbUrl, dbUser, dbPassword).start(4000);

        return 0;
    }
}
