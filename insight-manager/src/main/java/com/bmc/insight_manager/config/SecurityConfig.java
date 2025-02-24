package com.bmc.insight_manager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * SecurityConfig is a Spring configuration class that defines the security settings for the application.
 * <p>
 * This configuration sets up a SecurityFilterChain bean that:
 * <ul>
 *   <li>Disables CSRF protection.</li>
 *   <li>Requires authentication for all HTTP requests.</li>
 *   <li>Enables HTTP Basic authentication using default settings.</li>
 * </ul>
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures and returns a SecurityFilterChain bean.
     * <p>
     * This method customizes the HttpSecurity instance to disable CSRF protection,
     * require that every HTTP request is authenticated, and set up HTTP Basic authentication.
     *
     * @param http the HttpSecurity object used to configure web based security for specific http requests.
     * @return a SecurityFilterChain that defines the security filter chain for the application.
     * @throws Exception if an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
