package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public record Chambre(
        @ColumnName("id_hotel")
        UUID hotel,
        @ColumnName("numero_chambre")
        int numeroChambre,
        int places,
        float prix
) {
}