package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Client(
        @ColumnName("id_client")
        UUID id,
        String nom,
        String prenom,
        @Nested
        CarteBancaire carte
) {
}
