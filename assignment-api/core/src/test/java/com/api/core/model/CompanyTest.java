package com.api.core.model;

import com.api.core.config.SensitiveView;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;

import static org.junit.jupiter.api.Assertions.*;

public class CompanyTest {

    private Company company;
    private Validator validator;

    @BeforeEach
    public void setUp() {
        // Initialize the validator
        validator = Validation.buildDefaultValidatorFactory().getValidator();

        // Create a valid company
        company = new Company();
        company.setName("Tech Corp");
        company.setCountry("USA");
        company.setPhoneNumber("+123-456-7890");
    }

    @Test
    public void testCompanyProperties() {
        // Test valid company creation
        assertNotNull(company);
        assertEquals("Tech Corp", company.getName());
        assertEquals("USA", company.getCountry());
        assertEquals("+123-456-7890", company.getPhoneNumber());
    }

    @Test
    public void testCompanyValidation() {
        // Create a company with missing name and country
        Company invalidCompany = new Company();
        invalidCompany.setPhoneNumber("+123-456-7890");

        // Validate company fields and check for validation errors
        var violations = validator.validate(invalidCompany);

        // Assert that the required fields cause validation failures
        assertFalse(violations.isEmpty(), "Expected validation errors");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("name")), "Expected validation error for 'name'");
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("country")), "Expected validation error for 'country'");
    }

    @Test
    public void testJsonViews() throws Exception {
        // Use Jackson ObjectMapper to serialize the object
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize company to JSON using SensitiveView.Public
        String publicJson = objectMapper.writerWithView(SensitiveView.Public.class).writeValueAsString(company);
        // Assert that public fields are present in Public view (e.g., name, country)
        assertTrue(publicJson.contains("\"name\":\"Tech Corp\""));
        assertTrue(publicJson.contains("\"country\":\"USA\""));
        assertFalse(publicJson.contains("\"phoneNumber\":\"+123-456-7890\"")); // Phone is not in public view

        // Serialize company to JSON using SensitiveView.Admin (this should include phoneNumber)
        String adminJson = objectMapper.writerWithView(SensitiveView.Admin.class).writeValueAsString(company);
        assertTrue(adminJson.contains("\"phoneNumber\":\"+123-456-7890\""));
    }
}
