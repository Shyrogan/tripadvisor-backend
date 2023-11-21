package fr.samyseb.common;

import fr.samyseb.common.pojo.Adresse;
import fr.samyseb.common.pojo.Chambre;
import fr.samyseb.common.pojo.Hotel;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;
import org.jdbi.v3.postgres.PostgresPlugin;

public class Jdbis {

    public static Jdbi jdbiPostgre(String ip, String database, String username, String password) {
        return Jdbi.create("jdbc:postgresql://" + ip + "/" + database, username, password)
                .installPlugin(new PostgresPlugin())
                .registerRowMapper(Hotel.class, ConstructorMapper.of(Hotel.class))
                .registerRowMapper(Adresse.class, ConstructorMapper.of(Adresse.class))
                .registerRowMapper(Chambre.class, ConstructorMapper.of(Chambre.class));
    }

}
