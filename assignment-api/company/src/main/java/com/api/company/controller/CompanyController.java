package com.api.company.controller;

import com.api.core.config.SensitiveView;
import com.api.core.model.Company;
import com.api.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonView;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company", description = "Company API")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    // ✅ Create a new company
    @PostMapping
    @Operation(summary = "Create a new company")
    @PreAuthorize("hasRole('ADMIN')") // Correct role format: "ADMIN"
    public ResponseEntity<Company> createCompany(@RequestBody Company company) {
        Company createdCompany = companyService.createCompany(company);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCompany);  // 201 CREATED response
    }

    // ✅ Get all companies
    @GetMapping
    @Operation(summary = "Get all companies")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // Correct role format: "USER", "ADMIN"
    @JsonView(SensitiveView.Admin.class)  // Only expose the 'Public' view
    public List<Company> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    // ✅ Get company by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get company by ID")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")  // Correct role format
    @JsonView(SensitiveView.Admin.class)  // Only expose the 'Public' view
    public ResponseEntity<Company> getCompanyById(@PathVariable Long id) {
        Optional<Company> company = companyService.getCompanyById(id);
        return company.map(ResponseEntity::ok)  // Return 200 OK if found
                .orElseGet(() -> ResponseEntity.notFound().build());  // Return 404 if not found
    }

    // ✅ Update company details
    @PutMapping("/{id}")
    @Operation(summary = "Update company details")
    @PreAuthorize("hasRole('ADMIN')")  // Correct role format
    @JsonView(SensitiveView.Admin.class)  // Expose the 'Admin' view for admins
    public ResponseEntity<Company> updateCompany(@PathVariable Long id, @RequestBody Company companyDetails) {
        Optional<Company> updatedCompany = companyService.updateCompany(id, companyDetails);
        return updatedCompany.map(ResponseEntity::ok)  // Return 200 OK if updated
                .orElseGet(() -> ResponseEntity.notFound().build());  // Return 404 if not found
    }
}
