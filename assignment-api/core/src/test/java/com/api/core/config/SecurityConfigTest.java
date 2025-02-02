package com.api.core.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SecurityConfig.class) // Use the main application class here
@Import(SecurityConfig.class) // Import the specific config you're testing
class SecurityConfigTest {

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void securityFilterChain_ShouldNotBeNull() {
        assertNotNull(securityFilterChain);
    }

    @Test
    void userDetailsService_ShouldLoadUsers() {
        UserDetails admin = userDetailsService.loadUserByUsername("admin");
        UserDetails user = userDetailsService.loadUserByUsername("user");

        assertNotNull(admin);
        assertNotNull(user);
        assertThat(admin.getUsername()).isEqualTo("admin");
        assertThat(user.getUsername()).isEqualTo("user");
    }

    @Test
    void passwordEncoder_ShouldEncodePassword() {
        String rawPassword = "password123";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        assertNotNull(encodedPassword);
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword));
    }
}
