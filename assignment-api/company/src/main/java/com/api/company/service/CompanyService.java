package com.api.company.service;

import com.api.core.handler.filter.SensitiveDataHandler;
import com.api.core.model.Company;
import com.api.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final List<String> sensitiveFields = List.of("owners");

    // ✅ Create a new company
    @Transactional
    public Company createCompany(@Valid Company company) {
        return companyRepository.save(company);
    }

    // ✅ Get all companies
    public List<Company> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();
        return companies.stream()
                .map(company -> SensitiveDataHandler.filterSensitiveData(company, sensitiveFields))
                .collect(Collectors.toList());
    }

    // ✅ Get details of a specific company
    public Optional<Company> getCompanyById(Long id) {
        return SensitiveDataHandler.filterSensitiveData(companyRepository.findById(id), sensitiveFields);
    }

    // ✅ Update a company and return Optional<Company>
    @Transactional
    public Optional<Company> updateCompany(Long id, Company updatedCompany) {
        Optional<Company> existingCompany = companyRepository.findById(id);
        if (existingCompany.isPresent()) {
            Company company = existingCompany.get();
            company.setName(updatedCompany.getName());
            company.setCountry(updatedCompany.getCountry());
            company.setPhoneNumber(updatedCompany.getPhoneNumber());
            companyRepository.save(company);
            return Optional.of(SensitiveDataHandler.filterSensitiveData(company, sensitiveFields));  // Return wrapped in Optional
        }
        return Optional.empty();  // Return Optional.empty if not found
    }
}
