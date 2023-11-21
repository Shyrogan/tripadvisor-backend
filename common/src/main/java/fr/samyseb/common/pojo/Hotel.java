package fr.samyseb.common.pojo;

import io.javalin.validation.Validator;
import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;
import java.util.function.Function;

public record Hotel(
        @ColumnName("id_hotel")
        UUID id,
        String nom,
        int etoiles,
        @Nested
        Adresse adresse,
        String url
) {
    public static Function<Validator<Hotel>, Validator<Hotel>> VALIDATOR = v -> v
            .check(h -> h.nom().length() <= 64 && h.nom().length() >= 6 && !h.nom().isBlank(), "La nom de l'hôtel doit avoir une longueur comprise entre 6 et 64 caractères non vides.")
            .check(h -> h.etoiles() >= 1 && h.etoiles() <= 5, "Les étoiles de l'hôtel doivent-être comprisent entre 1 et 5");

}