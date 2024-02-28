package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.emailService.SendEmailService;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.Inventory;
import com.example.demo.repository.DonorDetailsRepository;

public class DonorDetailsServiceTest {

    @Mock
    private DonorDetailsRepository repo;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private SendEmailService emailService;

    @InjectMocks
    private DonorDetailsService service;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetDonorDetails() {
        // Arrange
        List<DonorDetails> expectedDonors = List.of(new DonorDetails(), new DonorDetails());

        // Mock repository behavior
        when(repo.findAll()).thenReturn(expectedDonors);

        // Act
        List<DonorDetails> donors = service.getDonorDetails();

        // Assert
        assertEquals(expectedDonors, donors);
        verify(repo, times(1)).findAll();
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        List<DonorDetails> expectedDonors = List.of(new DonorDetails(), new DonorDetails());

        // Mock repository behavior
        when(repo.findByEmail(email)).thenReturn(expectedDonors);

        // Act
        List<DonorDetails> donors = service.findByemail(email);

        // Assert
        assertEquals(expectedDonors, donors);
        verify(repo, times(1)).findByEmail(email);
    }

//    @Test
//    public void testCheckEligibility() {
//        // Arrange
//        List<DonorDetails> donors = new ArrayList<>();
//        DonorDetails eligibleDonor = new DonorDetails();
//        eligibleDonor.setDateOfDonation("2022-01-01");
//        donors.add(eligibleDonor);
//
//        // Mock repository behavior
//        when(repo.save(eligibleDonor)).thenReturn(eligibleDonor);
//        when(inventoryService.updateStatus(eligibleDonor)).thenReturn(eligibleDonor);
//
//        // Act
//        List<DonorDetails> eligibleDonors = service.checkEligibility(donors);
//
//        // Assert
//        assertEquals(1, eligibleDonors.size());
//        assertEquals(eligibleDonor, eligibleDonors.get(0));
//        verify(repo, times(1)).save(eligibleDonor);
//        verify(inventoryService, times(1)).updateStatus(eligibleDonor);
//        verify(emailService, times(1)).sendEmail(eligibleDonor.getEmail(),
//                "this is to inform you",
//                "you are now eligible to donate blood, Your Lifesaving Oasis, Open Every Hour, Every Day. Make a blood donation request, and donate according to your convenience");
//    }

    @Test
    public void testSaveDonorDetails() {
        // Arrange
        DonorDetails donorToSave = new DonorDetails();

        // Mock repository behavior
        when(repo.save(donorToSave)).thenReturn(donorToSave);

        // Act
        DonorDetails savedDonor = service.saveDonorDetails(donorToSave);

        // Assert
        assertEquals(donorToSave, savedDonor);
        verify(repo, times(1)).save(donorToSave);
    }

    @Test
    public void testGetDonorsDetails() {
        // Arrange
        List<DonorDetails> expectedDonors = List.of(new DonorDetails(), new DonorDetails());

        // Mock repository behavior
        when(repo.findAll()).thenReturn(expectedDonors);

        // Act
        List<DonorDetails> donors = service.getDonorsDetails();

        // Assert
        assertEquals(expectedDonors, donors);
        verify(repo, times(1)).findAll();
    }

    // Add more test cases for other methods as needed
}
