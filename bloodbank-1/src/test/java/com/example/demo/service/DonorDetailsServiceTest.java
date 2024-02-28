<<<<<<< HEAD
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
=======
//package com.example.demo.service;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.*;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import com.example.demo.entity.DonorDetails;
//import com.example.demo.repository.DonorDetailsRepository;
//import com.example.demo.service.DonorDetailsService;
//
//import jakarta.persistence.EntityManager;
//import jakarta.transaction.Transactional;
//
//
//@ActiveProfiles("test")
//@SpringBootTest
//@Transactional
//public class DonorDetailsServiceTest {
//	
//	@Autowired
//    private EntityManager entityManager;
//
//    @Mock
//    private DonorDetailsRepository donorDetailsRepository;
//
//    @InjectMocks
//    private DonorDetailsService donorDetailsService;
//
//    @BeforeEach
//    public void setup() {
//        MockitoAnnotations.openMocks(this);
//
//   
//        DonorDetails donor1 = new DonorDetails();
//        donor1.setEmail("test1@example.com");
//        donor1.setDateOfDonation("2022-01-01");
//        donor1.setStatus((byte) 1);
//
//        DonorDetails donor2 = new DonorDetails();
//        donor2.setEmail("test2@example.com");
//        donor2.setDateOfDonation("2022-01-05");
//        donor2.setStatus((byte) 1);
//
//       
//        List<DonorDetails> allDonors = List.of(donor1, donor2);
//        List<DonorDetails> donor1List = List.of(donor1);
//        List<DonorDetails> emptyDonorList = Collections.emptyList();
//
//
//        when(donorDetailsRepository.findAll()).thenReturn(allDonors);
//        when(donorDetailsRepository.findByEmail("test1@example.com")).thenReturn(donor1List);
//        when(donorDetailsRepository.findByEmail("test3@example.com")).thenReturn(emptyDonorList); // Test for non-existent email
//        when(donorDetailsRepository.findByStatus((byte) 1)).thenReturn(allDonors);
//        when(donorDetailsRepository.save(any())).thenReturn(donor1);
//    }
//
//    @Test
//    public void testGetDonorDetails() {
//
//        List<DonorDetails> resultList = donorDetailsService.getDonorDetails();
//
//
//        assertEquals(2, resultList.size());
//    }
//
//    @Test
//    public void testFindByEmail() {
//
//        List<DonorDetails> resultList = donorDetailsService.findByemail("test1@example.com");
//
//
//        assertEquals(1, resultList.size());
//        assertEquals("test1@example.com", resultList.get(0).getEmail());
//    }
//
//    @Test
//    public void testGetTotalDonationCount() {
//       
//        List<DonorDetails> resultList = donorDetailsService.getTotalDonationCount();
//
//        
//        assertEquals(2, resultList.size());
//    }
//
//    @Test
//    public void testCheckEligibility() {
//       
//        List<DonorDetails> resultList = donorDetailsService.checkEligibility(new ArrayList<>());
//
//        // Assert
//        assertNotNull(resultList);
//        assertEquals(0, resultList.size());
//    }
//    
//    
//    @Test
//    public void testSaveDonorDetails() {
//        // Arrange
//        DonorDetails donorToSave = new DonorDetails();
//        donorToSave.setEmail("test1@example.com");
//        donorToSave.setDateOfDonation("2022-02-01");
//        donorToSave.setStatus((byte) 1);
//
//        // Act
//        DonorDetails savedDonor = donorDetailsService.saveDonorDetails(donorToSave);
//
//        // Assert
//        assertNotNull(savedDonor);
//        assertEquals("test1@example.com", savedDonor.getEmail());
//    }
//
//
//    @Test
//    public void testGetDonorsDetails() {
//        // Act
//        List<DonorDetails> resultList = donorDetailsService.getDonorsDetails();
//
//        // Assert
//        assertEquals(2, resultList.size());
//        assertEquals("test1@example.com", resultList.get(0).getEmail());
//    }
//
//    @Test
//    public void testGetDonorsDetailsByEmail() {
//        // Act
//        List<DonorDetails> resultList = donorDetailsService.getDonorsDetailsByEmail("test1@example.com");
//
//        // Assert
//        assertEquals(1, resultList.size());
//        assertEquals("test1@example.com", resultList.get(0).getEmail());
//    }
//
//    @Test
//    public void testGetDonordetailsByStatus() {
//        // Act
//        List<DonorDetails> resultList = donorDetailsService.getDonordetailsByStatus((byte) 1);
//
//        // Assert
//        assertEquals(2, resultList.size());
//        assertEquals(1, resultList.get(0).getStatus());
//    }
//
//    @Test
//    public void testUpdateStatus() {
//        // Arrange
//        DonorDetails detail = new DonorDetails();
//        when(donorDetailsRepository.save(detail)).thenReturn(detail);
//
//        // Act
//        DonorDetails result = donorDetailsService.updateStatus(detail);
//
//        // Assert
//        assertNotNull(result);
//        assertEquals(detail, result);
//        assertEquals(detail.getStatus(), result.getStatus());
//    }
//
////    @Test
////    public void testGetDonorDetailsByEmailAndStatus() {
////        // Arrange
////        DonorDetails donorToSave = new DonorDetails();
////        donorToSave.setEmail("test1@example.com");
////        donorToSave.setDateOfDonation("2022-02-01");
////        donorToSave.setStatus((byte) 1);
////
////        // Act
////        donorDetailsService.saveDonorDetails(donorToSave);
////
////        // Explicitly flush and clear the session
////        entityManager.flush();
////        entityManager.clear();
////
////     // Debug information
////        System.out.println("---- Debug Info ----");
////        System.out.println("Number of donors in the database: " + donorDetailsRepository.findAll().size());
////        System.out.println("Details of donors in the database:");
////        donorDetailsRepository.findAll().forEach(donor -> System.out.println(donor.getEmail() + " - " + donor.getStatus()));
////        System.out.println("---- End Debug Info ----");
////
////        // Act
////        List<DonorDetails> resultList = donorDetailsService.getDonorDetailsByEmailAndStatus("test1@example.com", (byte) 1);
////
////        // Assert
////        assertEquals(1, resultList.size());
////        assertEquals("test1@example.com", resultList.get(0).getEmail());
////        assertEquals((byte) 1, resultList.get(0).getStatus());
////    }
//    
//    @Test
//    @Transactional
//    public void testGetDonorDetailsByEmailAndStatus() {
//        // Act
//        List<DonorDetails> resultList = donorDetailsService.getDonorDetailsByEmailAndStatus("test1@example.com", (byte) 1);
//
//        // Debug
//        System.out.println("Result List: " + resultList);
//
//        // Assert
//        assertEquals(1, resultList.size());
//        assertEquals("test1@example.com", resultList.get(0).getEmail());
//        assertEquals((byte) 1, resultList.get(0).getStatus());
//    }
//
//
//
//
//
//}
>>>>>>> 75da381059bb724e904f40a551b6cc13628882d8
