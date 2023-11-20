package fr.samyseb.common;

import io.javalin.config.JavalinConfig;
import io.javalin.plugin.bundled.CorsPluginConfig;

public class JavalinConfigs {

    JavalinConfigs() {}

    public static JavalinConfig commonConfiguration(JavalinConfig config) {
        config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
        config.plugins.enableDevLogging();
        return config;
    }

}
