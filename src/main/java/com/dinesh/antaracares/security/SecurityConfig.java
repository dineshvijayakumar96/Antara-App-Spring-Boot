package com.dinesh.antaracares.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTFilter jwtFilter;

    @Value("${security.allowed-origin}")
    private String allowedOrigin;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.cors(cors -> cors.configurationSource(request -> {
            var corsConfig = new org.springframework.web.cors.CorsConfiguration();
            corsConfig.setAllowedOrigins(List.of(allowedOrigin));
            corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfig.setAllowedHeaders(List.of("*"));
            corsConfig.setExposedHeaders(List.of("Content-Range"));
            corsConfig.setAllowCredentials(true);
            return corsConfig;
        }));

        httpSecurity.authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/api/home-contact-forms").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/api/service-contact-forms").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/api/home-contact-forms/**").hasRole("USER")
                                .requestMatchers(HttpMethod.GET, "/api/service-contact-forms/**").hasRole("USER")
                                .requestMatchers(HttpMethod.POST, "/api/home-contact-forms").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/service-contact-forms").permitAll()
                                .requestMatchers(HttpMethod.PUT, "/api/home-contact-forms/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/service-contact-forms/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/home-contact-forms/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/service-contact-forms/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.POST, "/api/register").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/users/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/api/users/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
                                .requestMatchers("/error").permitAll()
                                .anyRequest().authenticated()
        );

        httpSecurity.httpBasic(Customizer.withDefaults());

        httpSecurity.csrf(customizer -> customizer.disable());

        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
