package com.project.airost.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// 1. ADD THESE IMPORTS
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 2. ENABLE CORS HERE (Crucial Step!)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 1. Allow Register/Login/Verify
                        .requestMatchers("/api/auth/**").permitAll()

                        // ============================================================
                        // 2. FOUND ITEMS VISIBILITY RULES
                        // ============================================================

                        // Rule A: Specific path for "Success Stories" -> OPEN to everyone
                        .requestMatchers("/api/found-items/browse/claimed").permitAll()

                        // Rule B: Allow access to uploads folder so images can load
                        .requestMatchers("/uploads/").permitAll()

                        // Rule C: All other found-item paths -> LOCKED
                        .requestMatchers("/api/found-items/").authenticated()

                        // ============================================================

                        // 3. Admin action
                        .requestMatchers("/api/found-items/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/claims/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/claims/*/admin/**").hasRole("ADMIN") // For approve/reject



                        // 4. Everything else needs login
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 3. DEFINE THE CORS RULES (Add this method)
    @Bean
    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);

        // Allow your teammate's frontend URL.
        // using "*" allows ALL frontends (Good for development)
        config.addAllowedOriginPattern("*");

        config.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}