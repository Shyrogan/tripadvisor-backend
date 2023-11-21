package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Hotel(
        @ColumnName("id_hotel")
        UUID id,
        String nom,
        int etoiles,
        @Nested
        Adresse adresse,
        String url
) {
}