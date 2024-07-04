package com.sam.task_management.Config;

import com.sam.task_management.Security.CustomUserDetailsService;
import com.sam.task_management.Security.JwtAuthenticationEntryPoint;
import com.sam.task_management.Security.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * WebSecurityConfig class that configures the security settings for the application.
 *
 * @author LS Khiba
 * @version 1.0
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    /**
     * Constructor that initializes the dependencies.
     *
     * @param jwtAuthenticationEntryPoint the JWT authentication entry point
     * @param customUserDetailsService    the custom user details service
     * @param jwtRequestFilter            the JWT request filter
     */
    public WebSecurityConfig(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, CustomUserDetailsService customUserDetailsService, JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    /**
     * Bean that returns the password encoder.
     *
     * @return the password encoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean that returns the authentication manager.
     *
     * @param authenticationConfiguration the authentication configuration
     * @return the authentication manager
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean that returns the security filter chain.
     *
     * @param httpSecurity the HTTP security
     * @return the security filter chain
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers("/api/auth/**").permitAll()
                                // UserController matchers
                                .requestMatchers(HttpMethod.GET, "/api/users/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/users").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/users/{id}/password").hasAnyAuthority("ADMIN", "MEMBER")
                                .requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("ADMIN")
                                // TaskController matchers
                                .requestMatchers(HttpMethod.POST, "/api/tasks").hasAnyAuthority("ADMIN", "PROJECT_MANAGER")
                                .requestMatchers(HttpMethod.GET, "/api/tasks/{id}").authenticated()
                                .requestMatchers(HttpMethod.GET, "/api/tasks").hasAnyAuthority("ADMIN", "PROJECT_MANAGER", "TEAM_LEAD")
                                .requestMatchers(HttpMethod.GET, "/api/tasks/user/{id}").hasAuthority("MEMBER")
                                .requestMatchers(HttpMethod.PUT, "/api/tasks/{id}").hasAnyAuthority("ADMIN", "PROJECT_MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/api/tasks/{id}").hasAuthority("ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/tasks/{id}/assign").hasAnyAuthority("ADMIN", "PROJECT_MANAGER", "TEAM_LEAD")
                                .requestMatchers(HttpMethod.PATCH, "/api/tasks/{id}/unassign").hasAnyAuthority("ADMIN", "PROJECT_MANAGER", "TEAM_LEAD")
                                .requestMatchers(HttpMethod.PATCH, "/api/tasks/{id}/status").authenticated()
                                .requestMatchers(HttpMethod.PATCH, "/api/tasks/{id}/priority").hasAnyAuthority("ADMIN", "PROJECT_MANAGER", "TEAM_LEAD")
                                .requestMatchers(HttpMethod.PATCH, "/api/tasks/{id}/dueDate").hasAnyAuthority("ADMIN", "PROJECT_MANAGER", "TEAM_LEAD")
                                .anyRequest().authenticated()
                )
                .userDetailsService(customUserDetailsService)
                .exceptionHandling(e -> e.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
