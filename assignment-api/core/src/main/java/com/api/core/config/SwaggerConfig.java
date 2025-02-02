package com.api.core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Company and Owner API",
                version = "v1",
                description = "API for managing companies and owners",
                termsOfService = "http://example.com/terms",
                contact = @Contact(name = "API Support", url = "http://example.com/support", email = "support@example.com"),
                license = @License(name = "MIT", url = "http://opensource.org/licenses/MIT")
        )
)
public class SwaggerConfig {
    // Swagger is automatically configured through the annotations above
}
