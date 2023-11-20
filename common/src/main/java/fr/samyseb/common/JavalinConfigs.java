package fr.samyseb.common;

import io.javalin.config.JavalinConfig;
import io.javalin.openapi.plugin.OpenApiPlugin;
import io.javalin.openapi.plugin.OpenApiPluginConfiguration;
import io.javalin.plugin.bundled.CorsPluginConfig;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JavalinConfigs {

    public static JavalinConfig commonConfiguration(JavalinConfig config) {
        config.plugins.enableCors(cors -> cors.add(CorsPluginConfig::anyHost));
        config.plugins.enableDevLogging();
        return config;
    }

}
