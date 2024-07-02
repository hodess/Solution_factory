package org.example;

import classe.Create_class;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

@SpringBootApplication
public class Application {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Autoriser toutes les origines
        config.addAllowedOrigin("*");

        // Autoriser toutes les méthodes (GET, POST, PUT, DELETE, etc.)
        config.addAllowedMethod("*");

        // Autoriser tous les en-têtes
        config.addAllowedHeader("*");

        source.registerCorsConfiguration("/**", config);
        Properties properties = new Properties();
        try (InputStream input = Create_class.class.getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String port = properties.getProperty("server.port");
        logger.info("URL : http://localhost:"+port+"/hello");
        return new CorsFilter(source);
    }
}
