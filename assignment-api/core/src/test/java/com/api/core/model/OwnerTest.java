package com.api.core.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OwnerTest {

    @Test
    public void testOwnerCreation() {
        // Imitate creating an Owner object
        Owner owner = new Owner();
        owner.setId(1L);  // Pretend we set the ID
        owner.setName("John Doe");  // Pretend we set the name
        owner.setSocialSecurityNumber("123-45-6789");  // Pretend we set the SSN

        // Now, pretend we’re testing the values
        assertEquals(1L, owner.getId(), "Owner ID should be 1L");
        assertEquals("John Doe", owner.getName(), "Owner name should be 'John Doe'");
        assertEquals("123-45-6789", owner.getSocialSecurityNumber(), "Owner SSN should be '123-45-6789'");

        // Pretend that the creation logic worked fine, so the test passes
    }
}
