package fr.samyseb.agence;

import fr.samyseb.common.pojo.Agence;
import picocli.CommandLine;

import java.util.UUID;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "agence-service",
        description = "Démarre le service d'une agence en fournissant les informations nécessaire à son bon fonctionnement.")
public class Command implements Callable<Integer> {

    @CommandLine.Option(names = "ag_name", description = "Le nom affiché de l'agence", required = true)
    private String nom;
    @CommandLine.Option(names = "ag_password", description = "Le mot de passe de l'agence utilisé en interne", required = true)
    private String password;

    @CommandLine.Option(names = "jdbc_pg_url", description = "Le lien JDBC vers la base de donnée PostgreSQL (de la forme jdbc:postgresql://host/database)", required = true)
    private String jdbcUrl;
    @CommandLine.Option(names = "pg_user", defaultValue = "postgres",
            description = "Le nom d'utilisateur pour la connexion à PostgreSQL (défaut: postgres)")
    private String pgUser;
    @CommandLine.Option(names = "pg_password", defaultValue = "",
            description = "Le mot de passe (s'il y en a un) pour la connexion à PostgreSQL")
    private String pgPassword;

    @Override
    public Integer call() {
        new Application(new Agence(
                UUID.randomUUID(), nom, password,
                "http://%s:%d".formatted("localhost", 4000)
        ), jdbcUrl, pgUser, pgPassword).start(4000);

        return 0;
    }
}
