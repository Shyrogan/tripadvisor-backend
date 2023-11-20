package fr.samyseb.common.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data @NoArgsConstructor
public class Hotel {
    private UUID id;
    private Adresse adresse;
    private String nom;
    private int etoile;
}
