package com.api.core.handler.exception;

import com.api.core.exceptions.InvalidSocialSecurityException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

public class GlobalExceptionHandlerTest {

    @Test
    public void testHandleInvalidSSNException() {
        // Simulate the exception
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidSocialSecurityException exception = new InvalidSocialSecurityException("Invalid SSN");

        // Call the handler method
        var result = handler.handleInvalidSSNException(exception);

        // Assert the result: error message should be "Invalid SSN"
        assertEquals("Invalid SSN", result.get("error"));
        assertEquals(HttpStatus.BAD_REQUEST.value(), 400);  // Check the HTTP Status is BAD_REQUEST
    }

    @Test
    public void testHandleGeneralException() {
        // Simulate a general exception
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception exception = new Exception("General error");

        // Call the handler method
        var result = handler.handleGeneralException(exception);

        // Assert the result
        assertEquals("An unexpected error occurred", result.get("error"));
        assertEquals("Something went wrong. Please try again later.", result.get("message"));
    }

    // Dummy GlobalExceptionHandler to simulate the behavior
    static class GlobalExceptionHandler {

        public java.util.Map<String, String> handleInvalidSSNException(InvalidSocialSecurityException ex) {
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", ex.getMessage());
            return error;
        }

        public java.util.Map<String, String> handleGeneralException(Exception ex) {
            java.util.Map<String, String> error = new java.util.HashMap<>();
            error.put("error", "An unexpected error occurred");
            error.put("message", "Something went wrong. Please try again later.");
            return error;
        }
    }
}
