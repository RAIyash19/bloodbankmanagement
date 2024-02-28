package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.demo.entity.RegistrationDetails;
import com.example.demo.repository.RegistrationDetailsRepository;
import com.example.demo.service.RegistrationDetailsService;

public class RegistrationDetailsServiceTest {

    @Mock
    private RegistrationDetailsRepository repo;

    @InjectMocks
    private RegistrationDetailsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testSaveRegistrationDetails() {
//        // Arrange
//        RegistrationDetails detail = new RegistrationDetails();
//        detail.setPassword("plaintextpassword");
//
//        // Mock BCryptPasswordEncoder
//        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
//        when(passwordEncoder.encode("plaintextpassword")).thenReturn("hashedpassword");
//
//        // Inject the mock password encoder into the service
//        service.setPasswordEncoder(passwordEncoder);
//
//        // Act
//        String result = service.saveRegistrationDetails(detail);
//
//        // Assert
//        verify(repo, times(1)).save(detail);
//        assertEquals("Registered successfully", result);
//        assertEquals("hashedpassword", detail.getPassword());
//    }

    @Test
    public void testUpdateUserProfile() {
        RegistrationDetails detail = new RegistrationDetails();
        detail.setId(1);
        detail.setEmail("updated@example.com");

        when(repo.save(detail)).thenReturn(detail);

        RegistrationDetails updatedDetail = service.updateUserProfile(detail);

        assertEquals(1, updatedDetail.getId());
        assertEquals("updated@example.com", updatedDetail.getEmail());
        verify(repo).save(detail);
    }

    @Test
    public void testDeleteRegistrationDetail() {
        // Arrange
        List<RegistrationDetails> saved = new ArrayList<>();
        RegistrationDetails detail = new RegistrationDetails();
        saved.add(detail);

        when(repo.findAllByEmail("test@example.com")).thenReturn(saved);

        // Act
        service.deleteRegistrationDetail("test@example.com");

        // Assert
        verify(repo, times(1)).delete(detail);
    }

    @Test
    public void testGetRegistrationDetails() {
        List<RegistrationDetails> expectedDetails = List.of(new RegistrationDetails());
        when(repo.findAll()).thenReturn(expectedDetails);

        List<RegistrationDetails> details = service.getRegistrationDetails();

        assertEquals(expectedDetails, details);
        verify(repo).findAll();
    }

    @Test
    public void testGetRegistrationDetailsById() {
        RegistrationDetails expectedDetail = new RegistrationDetails();
        expectedDetail.setId(1);
        when(repo.findById(1)).thenReturn(Optional.of(expectedDetail));

        Optional<RegistrationDetails> detail = service.getRegistrationDetailsById(1);

        assertTrue(detail.isPresent());
        assertEquals(expectedDetail, detail.get());
        verify(repo).findById(1);
    }

    @Test
    public void testGetRegistrationDetailsByEmail() {
        String email = "test@example.com";
        List<RegistrationDetails> expectedDetails = List.of(new RegistrationDetails());
        when(repo.findAllByEmail(email)).thenReturn(expectedDetails);

        List<RegistrationDetails> details = service.getRegistrationDetailsByEmail("test@example.com");

        assertEquals(expectedDetails, details);
        verify(repo).findAllByEmail(email);
    }

    @Test
    public void testGetRegistrationDetailsByRole() {
        String role = "ADMIN";
        List<RegistrationDetails> expectedDetails = List.of(new RegistrationDetails());
        when(repo.findByRole(role)).thenReturn(expectedDetails);

        List<RegistrationDetails> details = service.getRegistrationDetailsByRole("ADMIN");

        assertEquals(expectedDetails, details);
        verify(repo).findByRole(role);
    }
    
    @Test
    public void testGetRegistrationDetailsByRoleWithNonExistingRole() {
        // Arrange
        when(repo.findByRole("NON_EXISTING_ROLE")).thenReturn(Collections.emptyList());

        // Act
        List<RegistrationDetails> details = service.getRegistrationDetailsByRole("NON_EXISTING_ROLE");

        // Assert
        assertTrue(details.isEmpty());
        verify(repo).findByRole("NON_EXISTING_ROLE");
    }
    
//    @Test
//    public void testSaveRegistrationDetailsWithExistingEmail() {
//        // Arrange
//        RegistrationDetails detail = new RegistrationDetails();
//        detail.setEmail("existing@example.com");
//
//        // Mock BCryptPasswordEncoder
//        BCryptPasswordEncoder passwordEncoder = mock(BCryptPasswordEncoder.class);
//        when(passwordEncoder.encode(anyString())).thenReturn("hashedpassword");
//
//        // Mock repository behavior
//        when(repo.findAllByEmail("existing@example.com")).thenReturn(List.of(detail));
//
//        // Inject the mock password encoder into the service
//        service.setPasswordEncoder(passwordEncoder);
//
//        // Act
//        String result = service.saveRegistrationDetails(detail);
//
//        // Assert
//        verify(repo, never()).save(detail); // Should not save if email already exists
//        assertEquals("Email already exists", result);
//    }

    @Test
    public void testUpdateUserProfileWithNonExistingId() {
        // Arrange
        RegistrationDetails detail = new RegistrationDetails();
        detail.setId(1);
        detail.setEmail("updated@example.com");

        // Mock repository behavior
        when(repo.findById(1)).thenReturn(Optional.empty()); // Simulate non-existing ID

        // Act
        RegistrationDetails updatedDetail = service.updateUserProfile(detail);

        // Assert
        assertNull(updatedDetail);
    }

    @Test
    public void testDeleteRegistrationDetailWithNonExistingEmail() {
        // Arrange
        when(repo.findAllByEmail("nonexistent@example.com")).thenReturn(Collections.emptyList());

        // Act
        service.deleteRegistrationDetail("nonexistent@example.com");

        // Assert
        verify(repo, never()).delete(any()); // Should not attempt deletion if email does not exist
    }

    @Test
    public void testGetRegistrationDetailsByEmailWithNonExistingEmail() {
        // Arrange
        when(repo.findAllByEmail("nonexistent@example.com")).thenReturn(Collections.emptyList());

        // Act
        List<RegistrationDetails> details = service.getRegistrationDetailsByEmail("nonexistent@example.com");

        // Assert
        assertTrue(details.isEmpty());
        verify(repo).findAllByEmail("nonexistent@example.com");
    }
}

