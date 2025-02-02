package com.api.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().ignoringAntMatchers("/api/**")  // Disable CSRF for API endpoints
                .and()
                .authorizeRequests()
                // Ensure Swagger-related URLs are explicitly accessible without login
                .antMatchers("/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs/**").permitAll()  // Swagger UI and docs
                .antMatchers("/", "/login", "/logout-screen").permitAll()  // Public access to other pages like login screen
                // Protect the rest of the URLs
                .anyRequest().authenticated()  // All other requests require authentication
                .and()
                .formLogin()
                .defaultSuccessUrl("/swagger-ui/index.html", true)  // Redirect to Swagger after login
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")  // Redirect after logout
                .invalidateHttpSession(true)  // Invalidate session
                .clearAuthentication(true)  // Clear authentication details
                .deleteCookies("JSESSIONID")  // Delete session cookies on logout
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Create session if required
                .and()
                .httpBasic()  // Enable basic authentication (you may also remove this if not needed)
                .and()
                .headers()
                .cacheControl().disable();  // Disable caching for sensitive pages like login/logout

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")  // Roles without the ROLE_ prefix
                .build();

        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("user"))
                .roles("USER")  // Roles without the ROLE_ prefix
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // Use BCrypt for secure password hashing
    }
}
