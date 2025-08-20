//package com.br.alura.Challenge_ForumHub.infra.security
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Configuration
//public class CorsConfigurations {
//
//    @Value("${cors.allowed-origins}")
//    private String[] allowedOrigins;
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(allowedOrigins));
//        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//        configuration.setAllowedHeaders(List.of("*"));
//        configuration.setAllowCredentials(true);
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
//}
package com.br.alura.Challenge_ForumHub.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfigurations {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // Permite requisições de QUALQUER origem.
        configuration.setAllowedOrigins(List.of("*"));

        // Permite QUALQUER método HTTP (GET, POST, etc.).
        configuration.setAllowedMethods(List.of("*"));

        // Permite QUALQUER cabeçalho na requisição.
        configuration.setAllowedHeaders(List.of("*"));

        // Embora seja permissivo, allowCredentials não pode ser usado com allowedOrigins("*").
        // No entanto, para a maioria dos testes, isto não será um problema imediato.
        // configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}