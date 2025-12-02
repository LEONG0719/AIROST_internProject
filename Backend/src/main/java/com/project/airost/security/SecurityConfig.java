package com.project.airost.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Allow Register/Login/Verify
                        .requestMatchers("/api/auth/**").permitAll()

                        // ============================================================
                        // 2. FOUND ITEMS VISIBILITY RULES (ORDER MATTERS!)
                        // ============================================================

                        // Rule A: Specific path for "Success Stories" -> OPEN to everyone
                        .requestMatchers("/api/found-items/browse/claimed").permitAll()

                        // Rule B: All other found-item paths (Active list, Details, Reporting) -> LOCKED
                        // This blocks /api/found-items from the public
                        .requestMatchers("/api/found-items/**").authenticated()

                        // ============================================================

                        // 3. Admin actions
                        .requestMatchers("/api/claims/*/admin/**").hasRole("ADMIN")

                        // 4. Everything else needs login
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}