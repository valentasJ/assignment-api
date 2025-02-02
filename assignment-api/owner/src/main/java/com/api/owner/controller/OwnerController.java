package com.api.owner.controller;

import com.api.core.config.SensitiveView;
import com.api.core.model.Owner;
import com.api.owner.service.OwnerService;
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
@RequestMapping("/api/owners")
@Tag(name = "Owner", description = "Manage Company Owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    // ✅ Create a new owner
    @PostMapping
    @Operation(summary = "Create a new owner")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Owner> createOwner(@RequestBody Owner owner) {
        Owner createdOwner = ownerService.createOwner(owner);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOwner);  // 201 CREATED response
    }

    // ✅ Add an owner to a company
    @PostMapping("/{companyId}")
    @Operation(summary = "Add an owner to a company", description = "Add an owner to a company.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Owner addOwner(@PathVariable Long companyId, @RequestBody Owner owner) {
        return ownerService.addOwnerToCompany(companyId, owner);
    }

    // ✅ Get all owners (Filter sensitive data if not admin)
    @GetMapping
    @Operation(summary = "Get all owners", description = "Retrieve a list of all company owners.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @JsonView(SensitiveView.Admin.class)  // Only expose non-sensitive data (Public view) for all users
    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    // ✅ Get owner by ID (Filter sensitive data if not admin)
    @GetMapping("/{id}")
    @Operation(summary = "Get owner by ID", description = "Retrieve an owner by their ID.")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @JsonView(SensitiveView.Admin.class)  // Only expose non-sensitive data (Public view) for all users
    public Optional<Owner> getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }
}
