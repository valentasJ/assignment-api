package com.api.core.handler.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Field;
import java.util.List;

public class SensitiveDataHandler {

    // Method to filter sensitive data based on the list of field paths (e.g., "owner.socialSecurityNumber")
    public static <T> T filterSensitiveData(T entity, List<String> sensitiveFields) {
        boolean isAdmin = isAdminUser(); // Check if the current user is an admin

        if (entity == null || sensitiveFields == null || sensitiveFields.isEmpty()) {
            return entity;  // If no sensitive fields or entity, return as is
        }

        for (String fieldPath : sensitiveFields) {
            // Split the field path by dot (.)
            String[] fieldParts = fieldPath.split("\\.");

            try {
                Object currentObject = entity;
                // Traverse the object hierarchy based on the field path
                for (int i = 0; i < fieldParts.length - 1; i++) {
                    Field field = currentObject.getClass().getDeclaredField(fieldParts[i]);
                    field.setAccessible(true);
                    currentObject = field.get(currentObject);  // Move to the next level in the hierarchy
                }

                // Now we are at the field we need to modify
                Field targetField = currentObject.getClass().getDeclaredField(fieldParts[fieldParts.length - 1]);
                targetField.setAccessible(true);

                // If the user is not an admin, set the sensitive field to null
                if (!isAdmin) {
                    targetField.set(currentObject, null);
                }

            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Handle the exception: Field not found or access issue
                e.printStackTrace(); // Log or handle the exception as needed
            }
        }

        return entity;
    }

    // Utility method to check if the current user is an admin
    private static boolean isAdminUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}
