package fr.samyseb.common.pojo;

import java.time.LocalDate;
import java.util.UUID;

public record Offre(

        UUID id,

        float prix,

        Chambre chambre,

        LocalDate debut,
        LocalDate fin
) {

}
