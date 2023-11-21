package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public class Chambre {

    private final UUID id;
    private final UUID hotel;
    private final int places;
    private final float prix;

    public Chambre(
            @ColumnName("id_chambre") UUID id, @ColumnName("id_hotel") UUID hotel,
            int places, float prix
    ) {
        this.id = id;
        this.hotel = hotel;
        this.places = places;
        this.prix = prix;
    }

    public UUID id() {
        return id;
    }

    public UUID hotel() {
        return hotel;
    }

    public int places() {
        return places;
    }

    public float prix() {
        return prix;
    }
}
