package com.maquillaje.maquillajebackend.config;

import com.maquillaje.maquillajebackend.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(List.of(
                "http://localhost:5173",
                "https://alta-belleza-frontend.vercel.app"
        ));

        configuration.setAllowedMethods(List.of(
                "GET",
                "POST",
                "PUT",
                "DELETE",
                "OPTIONS"
        ));

        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())

                .userDetailsService(customUserDetailsService)

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .authorizeHttpRequests(auth -> auth

                        // Permitir solicitudes OPTIONS (CORS)
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // Autenticación
                        .requestMatchers("/auth/registro").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/logout").permitAll()

                        // Recursos públicos
                        .requestMatchers("/error").permitAll()
                        .requestMatchers("/uploads/**").permitAll()

                        // API pública
                        .requestMatchers("/api/productos/**").permitAll()
                        .requestMatchers("/api/pedidos/**").permitAll()
                        .requestMatchers("/api/detalles/**").permitAll()
                        .requestMatchers("/api/favoritos/**").permitAll()
                        .requestMatchers("/api/contacto").permitAll()

                        // Roles
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/editor/**").hasRole("EDITOR")
                        .requestMatchers("/usuario/**").hasAnyRole("USUARIO", "ADMINISTRADOR")

                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("correo")
                        .passwordParameter("contrasena")
                        .defaultSuccessUrl("/usuario", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());

        return http.build();
    }
}