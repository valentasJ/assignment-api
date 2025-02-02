package com.api.core.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InvalidSocialSecurityExceptionTest {

    @Test
    public void testInvalidSocialSecurityExceptionMessage() {
        // Define the expected message
        String expectedMessage = "Invalid SSN provided";

        // Assert that the exception is thrown with the expected message
        InvalidSocialSecurityException exception = assertThrows(InvalidSocialSecurityException.class, () -> {
            throw new InvalidSocialSecurityException(expectedMessage);
        });

        // Check that the message passed to the exception matches the expected message
        assertEquals(expectedMessage, exception.getMessage());
    }
}
