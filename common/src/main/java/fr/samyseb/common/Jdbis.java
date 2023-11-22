package fr.samyseb.common;

import fr.samyseb.common.pojo.*;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.postgres.PostgresPlugin;

public class Jdbis {

    public static Jdbi jdbiPostgre(String ip, String username, String password) {
        return Jdbi.create(ip, username, password)
                .installPlugin(new PostgresPlugin())
                .registerRowMapper(Hotel.class, ConstructorMapper.of(Hotel.class))
                .registerRowMapper(Adresse.class, ConstructorMapper.of(Adresse.class))
                .registerRowMapper(Chambre.class, ConstructorMapper.of(Chambre.class))
                .registerRowMapper(CarteBancaire.class, ConstructorMapper.of(CarteBancaire.class))
                .registerRowMapper(Client.class, ConstructorMapper.of(Client.class))
                .registerRowMapper(Reservation.class, ConstructorMapper.of(Reservation.class))
                .registerRowMapper(Reservation.class, ConstructorMapper.of(Agence.class));
    }

}
