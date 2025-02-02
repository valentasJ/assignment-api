package com.api.core.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = SwaggerConfig.class) // Specify your main application class here
@Import(SwaggerConfig.class) // Import the SwaggerConfig explicitly
class SwaggerConfigTest {

    @Autowired
    private SwaggerConfig swaggerConfig;

    @Test
    void swaggerConfig_ShouldNotBeNull() {
        assertNotNull(swaggerConfig); // Check if SwaggerConfig bean is correctly loaded
    }
}
