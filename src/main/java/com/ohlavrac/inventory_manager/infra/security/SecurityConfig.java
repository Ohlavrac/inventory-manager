package com.ohlavrac.inventory_manager.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig {

    public static final String[] ENDPOINTS_WITH_AUTH_NOT_REQUIRED = {
        "/api/users",
        "/api/auth/register",
        "/api/auth/login",
    };

    private final SecurityFilter securityFilter;

    public SecurityConfig(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(
                        authorize -> authorize
                            .requestMatchers(HttpMethod.POST, ENDPOINTS_WITH_AUTH_NOT_REQUIRED).permitAll()
                            .requestMatchers(HttpMethod.GET, ENDPOINTS_WITH_AUTH_NOT_REQUIRED).permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/products").authenticated()
                            .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole("ADMIN", "EMPLOYER")
                            .requestMatchers(HttpMethod.PUT, "/api/products").hasAnyRole("ADMIN", "EMPLOYER")
                            .requestMatchers(HttpMethod.DELETE, "/api/products").hasAnyRole("ADMIN", "EMPLOYER")
                            .requestMatchers(HttpMethod.PUT, "/api/admin/**").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/user/details").authenticated()
                            .anyRequest().denyAll()
                    )
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
