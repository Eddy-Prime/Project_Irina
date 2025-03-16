package be.ucll.se.courses.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.net.URL;
import java.util.List;

@ConfigurationProperties(prefix = "cors")
public record CorsProperties(
                List<URL> allowedOrigins) {
}
