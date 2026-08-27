package cl.mapuescuela.api;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        packages("cl.mapuescuela.api");

        property(
                ServletProperties.FILTER_FORWARD_ON_404,
                true
        );

        property(
                ServerProperties.WADL_FEATURE_DISABLE,
                true
        );
    }
}