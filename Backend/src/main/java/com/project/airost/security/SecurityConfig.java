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
                        // 1. Allow Login, Register, and Email Verification
                        .requestMatchers("/api/auth/**").permitAll()

                        // 2. Allow Found Items (ADD THIS LINE)
                        // This lets you post found items without logging in during testing
                        .requestMatchers("/api/found-items/**").permitAll()

                        // 3. Only Admin can approve/reject claims
                        .requestMatchers("/api/claims/*/admin/**").hasRole("ADMIN")

                        // 4. Everything else (Submit Lost Claim, etc.) requires a Token
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}