package fr.samyseb.common.pojo;

import io.javalin.validation.Validator;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;
import java.util.function.Function;

public record Adresse(
        @ColumnName("id_adresse")
        UUID id,
        String numero,
        String rue,
        String ville,
        String pays
) {
    public static Function<Validator<Adresse>, Validator<Adresse>> VALIDATOR = v -> v
            .check(a -> a.numero().length() <= 4 && !a.numero().isBlank(), "Le numéro de l'adresse doit avoir une longueur comprise entre 1 et 4 caractères non vides.")
            .check(a -> a.rue().length() <= 64 && a.rue().length() >= 6 && !a.rue().isBlank(), "La rue de l'adresse doit avoir une longueur comprise entre 6 et 64 caractères non vides.")
            .check(a -> a.ville().length() <= 64 && a.ville().length() >= 6 && !a.ville().isBlank(), "La ville de l'adresse doit avoir une longueur comprise entre 6 et 64 caractères non vides.")
            .check(a -> a.pays().length() <= 32 && a.pays().length() >= 6 && !a.pays().isBlank(), "Le pays de l'adresse doit avoir une longueur comprise entre 6 et 32 caractères non vides.");
}