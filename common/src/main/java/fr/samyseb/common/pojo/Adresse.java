package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Adresse(
        @ColumnName("id_adresse")
        UUID id,
        String numero,
        String rue,
        String ville,
        String pays
) {
}