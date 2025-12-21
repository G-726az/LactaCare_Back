package ista.M4A2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // Permitir credenciales
        corsConfiguration.setAllowCredentials(true);
        
        // Usar AllowedOriginPatterns para compatibilidad con allowCredentials=true
        corsConfiguration.setAllowedOriginPatterns(Arrays.asList(
            "http://localhost:4200",
            "http://localhost:4300",
            "http://localhost:8080",
            "http://192.168.56.47*:*",
            "https://tu-dominio-frontend.com"
        ));
        
        // Headers permitidos
        corsConfiguration.setAllowedHeaders(Arrays.asList(
            "Origin", 
            "Content-Type", 
            "Accept", 
            "Authorization",
            "X-Requested-With", 
            "Access-Control-Request-Method", 
            "Access-Control-Request-Headers"
        ));
        
        // Headers expuestos al cliente
        corsConfiguration.setExposedHeaders(Arrays.asList(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials"
        ));
        
        // Métodos HTTP permitidos
        corsConfiguration.setAllowedMethods(Arrays.asList(
            "GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"
        ));
        
        // Tiempo de cache para preflight requests
        corsConfiguration.setMaxAge(3600L);
        
        // Registrar configuración para todos los endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        
        return new CorsFilter(source);
    }
}