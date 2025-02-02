package com.api.core.handler.filter;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.*;

public class SensitiveDataHandlerTest {

    @Test
    public void testFilterSensitiveDataForAdmin() {
        // Simulate admin user directly by setting a simple authority
        SecurityContextHolder.getContext().setAuthentication(new DummyAuthentication("ROLE_ADMIN"));

        // Test data
        TestEntity entity = new TestEntity("123-45-6789");

        // Run filter (admin should not nullify SSN)
        TestEntity result = SensitiveDataHandler.filterSensitiveData(entity, java.util.Collections.singletonList("socialSecurityNumber"));

        // Assert the SSN is not nullified for admin
        assertEquals("123-45-6789", result.getSocialSecurityNumber());
    }

    @Test
    public void testFilterSensitiveDataForNonAdmin() {
        // Simulate non-admin user directly
        SecurityContextHolder.getContext().setAuthentication(new DummyAuthentication("ROLE_USER"));

        // Test data
        TestEntity entity = new TestEntity("123-45-6789");

        // Run filter (non-admin should nullify SSN)
        TestEntity result = SensitiveDataHandler.filterSensitiveData(entity, java.util.Collections.singletonList("socialSecurityNumber"));

        // Assert the SSN is nullified for non-admin
        assertNull(result.getSocialSecurityNumber());
    }

    // Dummy Authentication class to simulate roles
    static class DummyAuthentication implements org.springframework.security.core.Authentication {
        private String role;

        public DummyAuthentication(String role) {
            this.role = role;
        }

        @Override
        public String getName() { return "dummy"; }

        @Override
        public boolean isAuthenticated() { return true; }

        @Override
        public java.util.Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
            return java.util.Collections.singletonList(new SimpleGrantedAuthority(role));
        }

        @Override
        public Object getCredentials() { return null; }

        @Override
        public Object getDetails() { return null; }

        @Override
        public Object getPrincipal() { return null; }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {}
    }

    // Simple test entity
    public static class TestEntity {
        private String socialSecurityNumber;
        public TestEntity(String ssn) { this.socialSecurityNumber = ssn; }
        public String getSocialSecurityNumber() { return socialSecurityNumber; }
    }
}
