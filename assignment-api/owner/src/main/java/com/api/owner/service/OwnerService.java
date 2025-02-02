package com.api.owner.service;

import com.api.core.exceptions.InvalidSocialSecurityException;
import com.api.core.handler.filter.SensitiveDataHandler;
import com.api.core.model.Company;
import com.api.core.model.Owner;
import com.api.company.repository.CompanyRepository;
import com.api.owner.repository.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final CompanyRepository companyRepository;
    private final Random random = new Random();
    private final List<String> sensitiveFields = List.of("socialSecurityNumber");


    // ✅ Create a new company
    @Transactional
    public Owner createOwner(@Valid Owner owner) {
        if (validateSocialSecurityNumber(owner.getSocialSecurityNumber())) {
            throw new InvalidSocialSecurityException("Invalid Social Security Number");
        }
        return ownerRepository.save(owner);
    }

    // ✅ Add an owner to a specific company
    public Owner addOwnerToCompany(Long companyId, Owner owner) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        owner.setCompany(company);
        return SensitiveDataHandler.filterSensitiveData(ownerRepository.save(owner), sensitiveFields);
    }

    // ✅ Get all owners (filter sensitive data if not admin)
    public List<Owner> getAllOwners() {
        List<Owner> owners = ownerRepository.findAll();

        return owners.stream()
                .map(owner -> SensitiveDataHandler.filterSensitiveData(owner, sensitiveFields))
                .collect(Collectors.toList());
    }

    // ✅ Get owner by ID (filter sensitive data if not admin)
    public Optional<Owner> getOwnerById(Long id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.map(o -> SensitiveDataHandler.filterSensitiveData(o, sensitiveFields));
    }

    // ✅ Simulated Social Security Number validation
    private boolean validateSocialSecurityNumber(String ssn) {
        return random.nextBoolean();
    }
}
