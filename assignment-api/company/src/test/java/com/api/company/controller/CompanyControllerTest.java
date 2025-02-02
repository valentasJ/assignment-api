package com.api.company.controller;

import com.api.core.model.Company;
import com.api.company.service.CompanyService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompanyControllerTest {

    @Mock
    private CompanyService companyService;

    @InjectMocks
    private CompanyController companyController;

    private Company company;

    @BeforeEach
    void setUp() {
        company = new Company();
        company.setId(1L);
        company.setName("Test Company");
    }

    @Test
    void createCompany_ShouldReturnCreatedCompany() {
        when(companyService.createCompany(any(Company.class))).thenReturn(company);

        ResponseEntity<Company> response = companyController.createCompany(company);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(company, response.getBody());
    }

    @Test
    void getAllCompanies_ShouldReturnListOfCompanies() {
        List<Company> companies = Arrays.asList(company);
        when(companyService.getAllCompanies()).thenReturn(companies);

        List<Company> response = companyController.getAllCompanies();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(company, response.get(0));
    }

    @Test
    void getCompanyById_ShouldReturnCompany_WhenFound() {
        when(companyService.getCompanyById(1L)).thenReturn(Optional.of(company));

        ResponseEntity<Company> response = companyController.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(company, response.getBody());
    }

    @Test
    void getCompanyById_ShouldReturnNotFound_WhenNotFound() {
        when(companyService.getCompanyById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Company> response = companyController.getCompanyById(1L);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateCompany_ShouldReturnUpdatedCompany_WhenFound() {
        when(companyService.updateCompany(eq(1L), any(Company.class))).thenReturn(Optional.of(company));

        ResponseEntity<Company> response = companyController.updateCompany(1L, company);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(company, response.getBody());
    }

    @Test
    void updateCompany_ShouldReturnNotFound_WhenNotFound() {
        when(companyService.updateCompany(eq(1L), any(Company.class))).thenReturn(Optional.empty());

        ResponseEntity<Company> response = companyController.updateCompany(1L, company);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}