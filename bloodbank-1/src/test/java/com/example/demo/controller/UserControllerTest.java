package com.example.demo.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.example.demo.configuration.CustomUserDetail;
import com.example.demo.entity.DonorDetails;
import com.example.demo.entity.PatientDetails;
import com.example.demo.entity.RegistrationDetails;
import com.example.demo.service.UserService;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService loginService;
    
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
//
//    @Test
//    void testViewProfileDetails() {
//        HttpSession session = mock(HttpSession.class);
//        Model model = mock(Model.class);
//        CustomUserDetail user = new CustomUserDetail();
//        user.setEmail("test@example.com");
//        when(session.getAttribute("user")).thenReturn(user);
//
//        List<RegistrationDetails> userDetails = new ArrayList<>();
//        // add sample user details to the list
//
//        when(loginService.getProfileDetails("test@example.com")).thenReturn(userDetails);
//
//        String result = userController.viewProfileDetails(session, model);
//
//        assertEquals("userProfile", result);
//        verify(model).addAttribute(eq("user"), eq(userDetails));
//    }

    @Test
    void testUpdateProfile() {
        RegistrationDetails registrationDetails = new RegistrationDetails();
        // set properties for registrationDetails

        Model model = mock(Model.class);

        String result = userController.updateProfile(registrationDetails, model);

        assertEquals("userProfile", result);
        verify(loginService).updateProfile(eq(registrationDetails));
        verify(model).addAttribute(eq("userData"), eq(registrationDetails));
        verify(model).addAttribute(eq("userUpdate"), eq("Details updated successfully"));
        verify(model).addAttribute(eq("user"), anyList()); // assuming getProfileDetails returns a list
    }

    @Test
    void testViewBloodRequestsDetails() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(session.getAttribute("userEmail")).thenReturn("test@example.com");

        List<PatientDetails> bloodRequests = new ArrayList<>();
        // add sample blood requests to the list

        when(loginService.getBloodRequestsDetails("test@example.com")).thenReturn(bloodRequests);

        String result = userController.viewBloodRequestsDetails(session, model);

        assertEquals("userBloodRequestHistory", result);
        verify(model).addAttribute(eq("bloodRequestHistory"), eq(bloodRequests));
    }

    @Test
    void testGetDonateRequestsDetails() {
        HttpSession session = mock(HttpSession.class);
        Model model = mock(Model.class);
        when(session.getAttribute("userEmail")).thenReturn("test@example.com");

        List<DonorDetails> donateRequests = new ArrayList<>();
        // add sample donate requests to the list

        when(loginService.getDonateRequestsDetails("test@example.com")).thenReturn(donateRequests);

        String result = userController.getDonateRequestsDerails(session, model);

        assertEquals("userDonationRequestHistory", result);
        verify(model).addAttribute(eq("donationRequestHistory"), eq(donateRequests));
    }
    
    
    @Test
    public void testDonateRequest() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Set up MockMvc

        // Assuming a valid DonorDetails object is created for testing
        DonorDetails donorDetails = new DonorDetails();
        donorDetails.setEmail("test@example.com"); // Replace with a valid email for testing
        donorDetails.setDateOfDonation("2024-02-27"); // Replace with a valid date for testing

        mockMvc.perform(post("/user/bloodDonationRequest")
                .param("received.email", donorDetails.getEmail())
                .param("received.dateOfDonation", donorDetails.getDateOfDonation()))
                .andExpect(status().isOk())
                .andExpect(view().name("donationRequest")); // Replace with the expected view name
    }



    
    @Test
    public void testBloodRequestSelf() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Set up MockMvc

        // Assuming a valid PatientDetails object is created for testing
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setEmail("test@example.com"); // Replace with a valid email for testing
        patientDetails.setBloodGroup("A+"); // Replace with a valid blood group for testing
        patientDetails.setBloodUnits(3); // Replace with a valid number of blood units for testing

        mockMvc.perform(MockMvcRequestBuilders.post("/user/bloodRequestSelf")
                .param("received.email", patientDetails.getEmail())
                .param("received.bloodGroup", patientDetails.getBloodGroup())
                .param("received.bloodUnits", String.valueOf(patientDetails.getBloodUnits())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bloodRequest")); // Replace with the expected view name
    }
    
    


    @Test
    public void testBloodRequestOthers() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build(); // Set up MockMvc

        // Assuming a valid PatientDetails object is created for testing
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setEmail("test@example.com"); // Replace with a valid email for testing
        patientDetails.setBloodGroup("A+"); // Replace with a valid blood group for testing
        patientDetails.setBloodUnits(3); // Replace with a valid number of blood units for testing

        mockMvc.perform(MockMvcRequestBuilders.post("/user/bloodRequestOthers")
                .param("received.email", patientDetails.getEmail())
                .param("received.bloodGroup", patientDetails.getBloodGroup())
                .param("received.bloodUnits", String.valueOf(patientDetails.getBloodUnits())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("bloodRequest")); // Replace with the expected view name
    }
    
//    @Test
//    public void testDeleteUser() throws Exception {
//        // Assuming a valid user email for testing
//        String userEmail = "test@example.com";
//
//        mockMvc.perform(post("/user/deleteUser").sessionAttr("userEmail", userEmail))
//               .andExpect(status().is3xxRedirection())
//               .andExpect(redirectedUrl("/userLogin"));
//
//        // Verify that deleteUser method is called with the correct email
//        verify(userService, times(1)).deleteUser(userEmail);
//    }
}
