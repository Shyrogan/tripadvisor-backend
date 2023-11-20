package fr.samyseb.common.pojo;

import java.util.UUID;

public record Adresse(
        UUID id,
        String numero,
        String rue,
        String ville,
        String pays
) {}