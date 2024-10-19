package com.example.demo.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;


import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, KeycloakRoleConverter keycloakRoleConverter) throws Exception {
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration cors1 = new CorsConfiguration();
                    cors1.setAllowedOrigins(List.of("http://localhost:4200"));
                    cors1.setAllowedMethods(Collections.singletonList("*"));
                    cors1.setAllowedHeaders(Collections.singletonList("*"));
                    cors1.setExposedHeaders(Collections.singletonList("Authorization"));
                    return cors1;
                }))
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .anyRequest().permitAll())
                .oauth2ResourceServer(rs -> rs.jwt(jwt -> jwt.jwtAuthenticationConverter(keycloakRoleConverter)));

        return http.build();
    }
}
