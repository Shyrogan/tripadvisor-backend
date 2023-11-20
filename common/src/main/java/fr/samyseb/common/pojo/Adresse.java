package fr.samyseb.common.pojo;

import io.javalin.http.Context;
import io.javalin.validation.Validator;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.util.function.Function;

@Data @NoArgsConstructor
public class Adresse {
    public static final Function<Validator<Adresse>, Validator<Adresse>> VALIDATOR = v -> v
            .check(a -> a.numero.length() > 4 || a.numero.isBlank(), "Le numéro de l'adresse doit avoir une longueur comprise entre 1 et 4 caractères non vides.")
            .check(a -> a.rue.length() > 64 || a.rue.length() < 6 || a.rue.isBlank(), "La rue de l'adresse doit avoir une longueur comprise entre 6 et 64 caractères non vides.")
            .check(a -> a.ville.length() > 64, "Ville invalide");

    private UUID id;
    private String numero;
    private String rue;
    private String ville;
    private String pays;
}