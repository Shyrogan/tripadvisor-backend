package fr.samyseb.common.pojo;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import java.util.UUID;

public class Adresse {
    private final UUID id;
    private final String numero;
    private final String rue;
    private final String ville;
    private final String pays;

    public Adresse(
            @ColumnName("id_adresse") UUID id, String numero,
            String rue, String ville, String pays
    ) {
        this.id = id;
        this.numero = numero;
        this.rue = rue;
        this.ville = ville;
        this.pays = pays;
    }

    public UUID id() {
        return id;
    }

    public String numero() {
        return numero;
    }

    public String rue() {
        return rue;
    }

    public String ville() {
        return ville;
    }

    public String pays() {
        return pays;
    }
    
}