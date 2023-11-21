package fr.samyseb.hotel.controller;

import fr.samyseb.common.pojo.Client;
import fr.samyseb.common.pojo.CarteBancaire;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;

import java.util.Map;
import java.util.UUID;

import static fr.samyseb.hotel.Application.application;


public class ClientController {

    public static void listClients(Context ctx) {
        ctx.json(application().jdbi().open()
                .createQuery("SELECT * FROM client")
                .mapTo(Client.class)
                .list());
    }

    public static void createClient(Context ctx) {
        var client = ctx.bodyValidator(Client.class)
                .check(c -> c.carte() != null, "Les informations de la carte bancaire sont nécessaires")
                .check(c -> c.carte().numero() != null && !c.carte().numero().isBlank(), "Le numéro de la carte bancaire est nécessaire")
                .get();

        UUID clientId = UUID.randomUUID();

        application().jdbi().open()
                .execute("INSERT INTO carte_bancaire (numero, mois, annee, cryptogramme) VALUES (?, ?, ?, ?)",
                        client.carte().numero(), client.carte().mois(), client.carte().annee(), client.carte().cryptogramme());

        application().jdbi().open()
                .execute("INSERT INTO client (id_client, nom, prenom, carte) VALUES (?, ?, ?, ?)",
                        clientId, client.nom(), client.prenom(), client.carte().numero());

        ctx.json(clientId).status(HttpStatus.CREATED);
    }


    public static void updateClient(Context ctx) {
        var client = ctx.bodyValidator(Client.class)
                .check(c -> c.nom() != null && !c.nom().isBlank(), "Le nom du client est nécessaire")
                .check(c -> c.prenom() != null && !c.prenom().isBlank(), "Le prénom du client est nécessaire")
                .get();

        if (client.id() != null) {
            // Mise à jour avec l'ID si fourni
            application().jdbi().open()
                    .execute("UPDATE client SET nom = ?, prenom = ?, carte = ? WHERE id_client = ?",
                            client.nom(), client.prenom(), client.carte().numero(), client.id());
        } else {
            // Mise à jour sans ID, en utilisant nom, prenom, et carte
            application().jdbi().open()
                    .execute("UPDATE client SET nom = ?, prenom = ?, carte = ? WHERE nom = ? AND prenom = ? AND carte = ?",
                            client.nom(), client.prenom(), client.carte().numero(), client.nom(), client.prenom(), client.carte().numero());
        }

        ctx.status(HttpStatus.OK);
    }

    public static void deleteClient(Context ctx) {
        var idClient = ctx.bodyValidator(UUID.class)
                .check(id -> id != null && application().jdbi().open()
                        .createQuery("SELECT * FROM client WHERE id_client = :id")
                        .bind("id", id)
                        .mapTo(Client.class)
                        .findFirst()
                        .isPresent(), "L'ID du client indiqué n'existe pas")
                .get();

        application().jdbi().open()
                .execute("DELETE FROM client WHERE id_client = ?", idClient);

        ctx.status(HttpStatus.OK);
    }


}
