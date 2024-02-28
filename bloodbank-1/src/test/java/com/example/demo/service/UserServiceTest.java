package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.repository.RegistrationDetailsRepository;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private RegistrationDetailsService registrationService;

    @Mock
    private PatientDetailsService patientService;

    @Mock
    private DonorDetailsService donorService;

    @Mock
    private InventoryService inventoryService;

    @Mock
    private SendEmailService emailService;

    @Mock
    private RegistrationDetailsRepository registrationRepo;

//    @Test
//    public void testCheckEmailExistence() {
//        RegistrationDetails testRegistrationDetails = new RegistrationDetails();
//        testRegistrationDetails.setEmail("test@example.com");
//
//        when(registrationService.getRegistrationDetailsByEmail(testRegistrationDetails.getEmail()))
//                .thenReturn(Arrays.asList());
//
//        int result = userService.checkEmailExistance(testRegistrationDetails);
//        assertEquals(0, result);
//    }

    @Test
    public void testGetProfileDetails() {
        String testEmail = "test@example.com";
        RegistrationDetails mockRegistration = new RegistrationDetails();
        mockRegistration.setEmail(testEmail);

        when(registrationService.getRegistrationDetailsByEmail(testEmail))
                .thenReturn(Arrays.asList(mockRegistration));

        List<RegistrationDetails> profileDetails = userService.getProfileDetails(testEmail);
        assertNotNull(profileDetails);
        assertEquals(1, profileDetails.size());
        assertEquals(testEmail, profileDetails.get(0).getEmail());
    }

//    @Test
//    public void testUpdateProfile() {
//        RegistrationDetails updatedDetails = new RegistrationDetails();
//        updatedDetails.setEmail("test@example.com");
//        updatedDetails.setFirstname("UpdatedFirstName");
//        updatedDetails.setLastname("UpdatedLastName");
//        // Set other fields as needed
//
//        when(registrationService.getRegistrationDetailsByEmail(updatedDetails.getEmail()))
//                .thenReturn(Arrays.asList(mock(RegistrationDetails.class)));
//
//        RegistrationDetails result = userService.updateProfile(updatedDetails);
//        assertNotNull(result);
//        assertEquals(updatedDetails.getEmail(), result.getEmail());
//        assertEquals(updatedDetails.getFirstname(), result.getFirstname());
//        assertEquals(updatedDetails.getLastname(), result.getLastname());
//        // Validate other fields
//    }

    @Test
    public void testGetBloodRequestsDetails() {
        String testEmail = "test@example.com";
        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());

        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(mockPatients);

        List<PatientDetails> bloodRequestsDetails = userService.getBloodRequestsDetails(testEmail);
        assertNotNull(bloodRequestsDetails);
        assertEquals(2, bloodRequestsDetails.size());
    }

//    @Test
//    public void testFindBloodDonationsCount() {
//        String testEmail = "test@example.com";
//        List<DonorDetails> mockDonors = Arrays.asList(new DonorDetails(), new DonorDetails(), new DonorDetails());
//
//        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(mockDonors);
//
//        int bloodDonationsCount = userService.findBloodDonationsCount(testEmail);
//        assertEquals(3, bloodDonationsCount);
//    }

//    @Test
//    public void testFindBloodRequestsCount() {
//        String testEmail = "test@example.com";
//        List<PatientDetails> mockPatients = Arrays.asList(new PatientDetails(), new PatientDetails());
//
//        when(patientService.getPatientsDetailsByEmail(testEmail)).thenReturn(mockPatients);
//
//        int bloodRequestsCount = userService.findBloodRequestsCount(testEmail);
//        assertEquals(2, bloodRequestsCount);
//    }

    @Test
    public void testGetDonateRequestsDetails() {
        String testEmail = "test@example.com";
        List<DonorDetails> mockDonors = Arrays.asList(new DonorDetails(), new DonorDetails());

        when(donorService.getDonorsDetailsByEmail(testEmail)).thenReturn(mockDonors);

        List<DonorDetails> donateRequestsDetails = userService.getDonateRequestsDetails(testEmail);
        assertNotNull(donateRequestsDetails);
        assertEquals(2, donateRequestsDetails.size());
    }

//    @Test
//    public void testDonateRequest() {
//        DonorDetails donorDetails = new DonorDetails();
//        donorDetails.setEmail("test@example.com");
//
//        when(donorService.getDonorDetailsByEmailAndStatus("test@example.com", (byte) 0)).thenReturn(new ArrayList<>());
//        when(donorService.getDonorDetailsByEmailAndStatus("test@example.com", (byte) 1)).thenReturn(new ArrayList<>());
//        when(donorService.saveDonorDetails(any(DonorDetails.class))).thenReturn(null);
//
//        int result = userService.donateRequest(donorDetails);
//        assertEquals(1, result);
//    }
//
//    @Test
//    public void testBloodRequestSelf() {
//        PatientDetails patientDetails = new PatientDetails();
//        patientDetails.setEmail("test@example.com");
//        patientDetails.setBloodUnits(3);
//
//        when(patientService.getPatientDetailsByEmailAndStatus("test@example.com", (byte) 0)).thenReturn(new ArrayList<>());
//        when(patientService.savePatientDetails(any(PatientDetails.class))).thenReturn(null);
//
//        int result = userService.bloodRequestSelf(patientDetails);
//        assertEquals(3, result);
//    }

//    @Test
//    public void testBloodRequestOthers() {
//        PatientDetails patientDetails = new PatientDetails();
//        patientDetails.setEmail("test@example.com");
//        patientDetails.setBloodUnits(3);
//
//        when(patientService.getPatientDetailsByEmailAndStatus("test@example.com", (byte) 0)).thenReturn(new ArrayList<>());
//        when(patientService.savePatientDetails(any(PatientDetails.class))).thenReturn(null);
//
//        int result = userService.bloodRequestOthers(patientDetails);
//        assertEquals(3, result);
//    }

    @Test
    public void testSendOtp() {
        String testEmail = "test@example.com";

        when(registrationService.getRegistrationDetailsByEmail(testEmail))
                .thenReturn(Arrays.asList(mock(RegistrationDetails.class)));

        int result = userService.sendOtp(testEmail);
        assertEquals(1, result);
    }

    @Test
    public void testResetPassword() {
        String testEmail = "test@example.com";
        int testOtp = 123456;
        String newPassword = "newPassword";

        RegistrationDetails mockRegistration = new RegistrationDetails();
        mockRegistration.setEmail(testEmail);
        mockRegistration.setOtp(testOtp);

        when(registrationService.getRegistrationDetailsByEmail(testEmail))
                .thenReturn(Arrays.asList(mockRegistration));

        int result = userService.resetPassword(testEmail, testOtp, newPassword, mock(Model.class));
        assertEquals(1, result);
    }

    @Test
    public void testForgetPasswordSendOtp() {
        String testEmail = "newuser@example.com";

        RegistrationDetails mockRegistration = new RegistrationDetails();
        mockRegistration.setEmail(testEmail);

        when(registrationService.getRegistrationDetailsByEmail(testEmail))
                .thenReturn(Arrays.asList(mockRegistration));

        int result = userService.forgetPasswordSendOtp(testEmail);
        assertEquals(1, result);
    }

    @Test
    public void testValidateUserDetails() {
        DonorDetails donorDetails = new DonorDetails();
        donorDetails.setEmail("test@example.com");

        when(registrationService.getRegistrationDetailsByEmail(donorDetails.getEmail()))
                .thenReturn(Arrays.asList(new RegistrationDetails()));

        boolean result = userService.validateUserDetails(donorDetails);
        assertTrue(result);
    }

    @Test
    public void testValidateUserDetailsForPatient() {
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setEmail("test@example.com");

        when(registrationService.getRegistrationDetailsByEmail(patientDetails.getEmail()))
                .thenReturn(Arrays.asList(new RegistrationDetails()));

        boolean result = userService.validateUserDetailsForPatient(patientDetails);
        assertTrue(result);
    }

    @Test
    public void testDeleteUser() {
        String testEmail = "test@example.com";

        userService.deleteUser(testEmail);

        verify(registrationRepo, times(1)).deleteByEmail(testEmail);
    }
}
