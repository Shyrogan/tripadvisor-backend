package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Chambre(
        @ColumnName("id_chambre")
        UUID id,
        @ColumnName("id_hotel")
        UUID hotel,
        int places,
        float prix
) {
}
