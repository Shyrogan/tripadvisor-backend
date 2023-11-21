package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.time.LocalDate;
import java.util.UUID;

public record Reservation(
        @ColumnName("id_reservation")
        UUID id,
        @ColumnName("id_hotel")
        UUID hotel,
        @ColumnName("id_chambre")
        UUID chambre,
        @Nested
        Client client,
        LocalDate debut,
        LocalDate fin
) {
}
