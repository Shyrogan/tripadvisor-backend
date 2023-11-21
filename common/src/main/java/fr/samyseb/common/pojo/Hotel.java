package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.Nested;
import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public class Hotel {

    private final UUID id;
    private final String nom;
    private final int etoiles;
    private final Adresse adresse;
    private final String url;

    public Hotel(@ColumnName("id_hotel") UUID id, String nom, int etoiles, @Nested Adresse adresse, String url) {
        this.id = id;
        this.nom = nom;
        this.etoiles = etoiles;
        this.adresse = adresse;
        this.url = url;
    }

    public UUID id() {
        return id;
    }

    public String nom() {
        return nom;
    }

    public int etoiles() {
        return etoiles;
    }

    public Adresse adresse() {
        return adresse;
    }

    public String url() {
        return url;
    }
}