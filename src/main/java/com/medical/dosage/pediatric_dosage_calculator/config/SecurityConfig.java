package com.medical.dosage.pediatric_dosage_calculator.config;

import com.medical.dosage.pediatric_dosage_calculator.security.JwtAuthFilter;
import com.medical.dosage.pediatric_dosage_calculator.security.JwtUtil;
import com.medical.dosage.pediatric_dosage_calculator.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Exponer AuthenticationManager para el AuthController
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        JwtAuthFilter jwtFilter = new JwtAuthFilter(jwtUtil, userDetailsService);

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/medicines").authenticated()          // GET listado → cualquier rol con login
                .requestMatchers("/api/medicines/{id}").authenticated()     // GET por id → cualquier rol con login
                .requestMatchers("/api/medicines/**").hasAuthority("ADMIN") // POST/PUT/DELETE → solo admin
                .requestMatchers("/api/calculator/**").authenticated() // permiso para calculadora (cualquier usuario)
                .requestMatchers("/api/ai/**").authenticated() // permiso para IA (cualquier usuario)
                .anyRequest().authenticated()
            );
       
        // agregar filtro JWT
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
