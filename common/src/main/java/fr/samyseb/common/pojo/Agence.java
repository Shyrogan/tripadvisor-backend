package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Agence(
        @ColumnName("id_agence")
        UUID id,
        String nom,
        String password,
        String url
) {
}
