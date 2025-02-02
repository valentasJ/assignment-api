package com.api.owner.controller;

import com.api.core.model.Owner;
import com.api.owner.service.OwnerService;
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
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController ownerController;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setId(1L);
        owner.setName("John Doe");
    }

    @Test
    void createOwner_ShouldReturnCreatedOwner() {
        when(ownerService.createOwner(any(Owner.class))).thenReturn(owner);

        ResponseEntity<Owner> response = ownerController.createOwner(owner);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(owner, response.getBody());
    }

    @Test
    void addOwner_ShouldReturnAddedOwner() {
        when(ownerService.addOwnerToCompany(eq(1L), any(Owner.class))).thenReturn(owner);

        Owner response = ownerController.addOwner(1L, owner);

        assertNotNull(response);
        assertEquals(owner, response);
    }

    @Test
    void getAllOwners_ShouldReturnListOfOwners() {
        List<Owner> owners = Arrays.asList(owner);
        when(ownerService.getAllOwners()).thenReturn(owners);

        List<Owner> response = ownerController.getAllOwners();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(owner, response.get(0));
    }

    @Test
    void getOwnerById_ShouldReturnOwner_WhenFound() {
        when(ownerService.getOwnerById(1L)).thenReturn(Optional.of(owner));

        Optional<Owner> response = ownerController.getOwnerById(1L);

        assertTrue(response.isPresent());
        assertEquals(owner, response.get());
    }

    @Test
    void getOwnerById_ShouldReturnEmpty_WhenNotFound() {
        when(ownerService.getOwnerById(1L)).thenReturn(Optional.empty());

        Optional<Owner> response = ownerController.getOwnerById(1L);

        assertFalse(response.isPresent());
    }
}
