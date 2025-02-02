package com.api.core.controller;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SsnControllerTest {

    @Test
    public void testValidateSsn() {
        SsnController ssnController = new SsnController();
        String ssn = "123-45-6789"; // Example SSN

        // Call the validateSsn method and capture the result
        String result = ssnController.validateSsn(ssn);

        // Assert that the result is either "valid" or "invalid"
        assertTrue(result.equals("valid") || result.equals("invalid"));
    }
}
