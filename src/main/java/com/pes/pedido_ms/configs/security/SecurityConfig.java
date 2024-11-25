package com.pes.pedido_ms.configs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfig {

        private static final String[] PUBLIC = {
                        "/oauth2/**",
                        "/swagger/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
        };

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http,
                        JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
                http.csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(PUBLIC).permitAll()
                                                .anyRequest().authenticated()
                                )
                                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class);

                return http.build();
        }
}