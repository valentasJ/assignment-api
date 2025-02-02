package com.api.core.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
@RequestMapping("/api/ssn")
@Tag(name = "SSN", description = "Social Security Number API")
public class SsnController {

    @GetMapping("/validate/{ssn}")
    @PreAuthorize("hasRole('ADMIN')") // Restricts to ADMIN only
    public String validateSsn(@PathVariable String ssn) {
        boolean isValid = new Random().nextBoolean();
        return isValid ? "valid" : "invalid";
    }
}
