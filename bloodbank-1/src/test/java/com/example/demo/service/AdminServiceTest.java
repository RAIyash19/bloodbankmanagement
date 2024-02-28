package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import com.example.demo.emailService.SendEmailService;
import com.example.demo.entity.BloodGroupDetails;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.Inventory;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;

@ExtendWith(MockitoExtension.class)
public class AdminServiceTest {

    @InjectMocks
    private AdminService adminService;

    @Mock
    private RegistrationDetailsService registrationService;

    @Mock
    private DonorDetailsService donorService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private PatientDetailsService patientService;

    @Mock
    private SendEmailService emailService;

    @Test
    public void testVerifyLogin_SuccessfulLogin() {
        RegistrationDetails received = new RegistrationDetails();
        received.setEmail("bhanumeghana2002@gmail.com");
        received.setPassword("123456");

        RegistrationDetails savedAdmin = new RegistrationDetails();
        savedAdmin.setEmail("bhanumeghana2002@gmail.com");
        savedAdmin.setPassword("$2a$10$DmHqaTF7lcdNZ6kT8RKqheU.YhjiuxqhOStwDhvYN6BOjEDHMEM/G"); // Hashed password

        when(registrationService.getRegistrationDetailsByRole("admin")).thenReturn(Arrays.asList(savedAdmin));

        byte result = adminService.verifyLogin(received);
        assertEquals(1, result);
    }

    @Test
    public void testVerifyLogin_IncorrectPassword() {
        RegistrationDetails received = new RegistrationDetails();
        received.setEmail("admin@example.com");
        received.setPassword("wrongPassword");

        RegistrationDetails savedAdmin = new RegistrationDetails();
        savedAdmin.setEmail("admin@example.com");
        savedAdmin.setPassword("$2a$10$3xQNZFVzR74vp1HrkCCqFO.HFSCbHW3kyI2/bn/k0YRSPtwsZC9oi"); // Hashed password

        when(registrationService.getRegistrationDetailsByRole("admin")).thenReturn(Arrays.asList(savedAdmin));

        byte result = adminService.verifyLogin(received);
        assertEquals(-1, result);
    }

    @Test
    public void testVerifyLogin_UserNotFound() {
        RegistrationDetails received = new RegistrationDetails();
        received.setEmail("nonexistent@example.com");
        received.setPassword("password");

        when(registrationService.getRegistrationDetailsByRole("admin")).thenReturn(new ArrayList<>());

        byte result = adminService.verifyLogin(received);
        assertEquals(0, result);
    }

    @Test
    public void testGetDonationRequests() {
        List<DonorDetails> mockDonors = Arrays.asList(new DonorDetails(), new DonorDetails());

        when(donorService.getDonordetailsByStatus((byte) 0)).thenReturn(mockDonors);

        List<DonorDetails> donationRequests = adminService.getDonationRequests();
        assertNotNull(donationRequests);
        assertEquals(2, donationRequests.size());
    }

    @Test
    public void testGetDonationHistory() {
        List<DonorDetails> mockDonors = Arrays.asList(new DonorDetails(), new DonorDetails());

        when(donorService.getDonorDetails()).thenReturn(mockDonors);

        List<DonorDetails> donationHistory = adminService.getDonationHistory();
        assertNotNull(donationHistory);
        assertEquals(2, donationHistory.size());
    }

    @Test
    public void testGetBloodRequestsHistory() {
        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());

        when(patientService.getBloodRequestsHistory()).thenReturn(mockPatients);

        List<PatientDetails> bloodRequestsHistory = adminService.getBloodRequestsHistory();
        assertNotNull(bloodRequestsHistory);
        assertEquals(2, bloodRequestsHistory.size());
    }

    @Test
    public void testGetBloodRequest() {
        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());

        when(patientService.getBloodRequestDetailsByStatus((byte) 0)).thenReturn(mockPatients);

        List<PatientDetails> bloodRequest = adminService.getBloodRequest();
        assertNotNull(bloodRequest);
        assertEquals(2, bloodRequest.size());
    }

//    @Test
//    public void testAcceptDonationRequest_Success() {
//        String testEmail = "test@example.com";
//        DonorDetails mockDonor = new DonorDetails();
//        mockDonor.setEmail(testEmail);
//        mockDonor.setStatus((byte) 0);
//
//        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockDonor));
//        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString()); // Assuming sendEmail doesn't return anything
//        when(donorService.saveDonorDetails(any(DonorDetails.class))).thenReturn(mockDonor);
//
//        String result = adminService.acceptDonationRequest(testEmail);
//        assertEquals("Donation request accepted for the user with email", result); // Fix: Add a space after "email"
//    }
//
//
//    
//    @Test
//    public void testAcceptDonationRequest_AlreadyAccepted() {
//        String testEmail = "bhanumeghana2002@gmail.com";
//        DonorDetails mockDonor = new DonorDetails();
//        mockDonor.setEmail(testEmail);
//        mockDonor.setStatus((byte) 1);
//
//        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockDonor));
//
//        String result = adminService.acceptDonationRequest(testEmail);
//        assertEquals("why are you accepting again and again, go and do some other work", result);
//    }
//
//
//    @Test
//    public void testRejectDonationRequest_Success() {
//        String testEmail = "test@example.com";
//        DonorDetails mockDonor = new DonorDetails();
//        mockDonor.setEmail(testEmail);
//        mockDonor.setStatus((byte) 0);
//
//        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockDonor));
//        when(donorService.updateStatus(any(DonorDetails.class))).thenReturn(mockDonor); // Change this line
//        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
//
//        String result = adminService.rejectDonationRequest(testEmail);
//        assertEquals("Blood Donation request rejected", result);
//    }
//
//
//
//
//    @Test
//    public void testRejectDonationRequest_NoRequestToReject() {
//        String testEmail = "test@example.com";
//
//        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(new ArrayList<>());
//
//        String result = adminService.rejectDonationRequest(testEmail);
//        assertEquals("No Blood Donation request is there to reject", result);
//    }
//
//

    @Test
    public void testViewBloodRequest() {
        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());

        when(patientService.getPatientDetailsByStatus((byte) 0)).thenReturn(mockPatients);

        List<PatientDetails> bloodRequests = adminService.viewBloodRequest();
        assertNotNull(bloodRequests);
        assertEquals(2, bloodRequests.size());
    }

//    @Test
//    public void testAcceptBloodRequest_Success() {
//        String testEmail = "test@example.com";
//        PatientDetails mockPatient = new PatientDetails();
//        mockPatient.setEmail(testEmail);
//        mockPatient.setStatus((byte) 0);
//        mockPatient.setBloodUnits(3);
//
//        Inventory mockInventory1 = new Inventory();
//        mockInventory1.setBloodGroup("A+");
//        mockInventory1.setQuantity(2);
//
//        Inventory mockInventory2 = new Inventory();
//        mockInventory2.setBloodGroup("A-");
//        mockInventory2.setQuantity(1);
//
//        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockPatient));
//        when(inventoryService.getInventoryDetailsByBloodGroup("A+")).thenReturn(Arrays.asList(mockInventory1));
//        when(inventoryService.getInventoryDetailsByBloodGroup("A-")).thenReturn(Arrays.asList(mockInventory2));
//        when(inventoryService.deleteInventory(any(Inventory.class))).thenReturn(null);
//        when(patientService.savePatientDetails(any(PatientDetails.class))).thenReturn(null);
//
//        String result = adminService.acceptBloodRequest(testEmail);
//        assertEquals("Given 3 units of blood successfully", result);
//    }

//    @Test
//    public void testAcceptBloodRequest_AlreadyAccepted() {
//        String testEmail = "test@example.com";
//        PatientDetails mockPatient = new PatientDetails();
//        mockPatient.setEmail(testEmail);
//        mockPatient.setStatus((byte) 1);
//
//        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockPatient));
//
//        String expectedMessage = "why are you accepting again and again, go and do some other work";
//        String result = adminService.acceptBloodRequest(testEmail);
//        assertEquals(expectedMessage, result);
//    }
//


//    @Test
//    public void testRejectBloodRequest_Success() {
//        String testEmail = "test@example.com";
//        PatientDetails mockPatient = new PatientDetails();
//        mockPatient.setEmail(testEmail);
//        mockPatient.setStatus((byte) 0);
//
//        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(Arrays.asList(mockPatient));
//        when(emailService.sendEmail(anyString(), anyString(), anyString())).thenReturn(null);
//
//        String result = adminService.rejectBloodRequest(testEmail);
//        assertEquals("Blood request rejected", result);
//    }

//    @Test
//    public void testRejectBloodRequest_NoRequestToReject() {
//        String testEmail = "test@example.com";
//
//        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(new ArrayList<>());
//
//        String result = adminService.rejectBloodRequest(testEmail);
//        assertEquals("No Blood request is there to reject", result);
//    }

    @Test
    public void testUpdateUserProfile() {
        RegistrationDetails user = new RegistrationDetails();
        user.setEmail("test@example.com");

        List<RegistrationDetails> savedDetails = Arrays.asList(new RegistrationDetails());

        when(registrationService.getRegistrationDetailsByEmail(user.getEmail())).thenReturn(savedDetails);
        when(registrationService.updateUserProfile(any(RegistrationDetails.class))).thenReturn(null);

        RegistrationDetails result = adminService.updateUserProfile(user);
        assertNull(result);
    }

    @Test
    public void testGetProfileDetails() {
        RegistrationDetails detail = new RegistrationDetails();
        detail.setEmail("test@example.com");

        List<RegistrationDetails> savedDetails = Arrays.asList(new RegistrationDetails());

        when(registrationService.getRegistrationDetailsByEmail(detail.getEmail())).thenReturn(savedDetails);

        List<RegistrationDetails> result = adminService.getProfileDetails(detail);
        assertNotNull(result);
        assertEquals(1, result.size());
    }

//    @Test
//    public void testGetDetail() {
//        BloodGroupDetails mockAPlus = new BloodGroupDetails("A+", 10);
//        BloodGroupDetails mockBMinus = new BloodGroupDetails("B-", 5);
//
//        when(inventoryService.getBloodCount("A+")).thenReturn(10);
//        when(inventoryService.getBloodCount("B-")).thenReturn(5);
//
//        List<BloodGroupDetails> result = adminService.getDetail();
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(mockAPlus, result.get(0));
//        assertEquals(mockBMinus, result.get(1));
//    }


    @Test
    public void testCountBloodRequest() {
        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());

        when(patientService.getTotalRequestCount()).thenReturn(mockPatients);

        int result = adminService.countBloodRequest();
        assertEquals(2, result);
    }
}
