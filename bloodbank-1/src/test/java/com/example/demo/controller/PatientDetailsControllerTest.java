package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import com.example.demo.entity.PatientDetails;
import com.example.demo.service.PatientDetailsService;

@SpringBootTest
class PatientDetailsControllerTest {

    @InjectMocks
    private PatientDetailsController controller;

    @Mock
    private PatientDetailsService service;

    @Test
    void testSavePatientDetails_Success() {
        // Arrange
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setId(1L);
        patientDetails.setFirstname("John Doe");
        patientDetails.setEmail("john@example.com");

        when(service.savePatientDetails(patientDetails)).thenReturn(patientDetails);

        // Act
        PatientDetails result = controller.savePatientDetailsC(patientDetails);

        // Assert
        assertEquals(patientDetails, result);
    }

    @Test
    void testGetPatientsDetails_Success() {
        // Arrange
        PatientDetails patient1 = new PatientDetails();
        patient1.setId(1L);
        patient1.setFirstname("John Doe");
        patient1.setEmail("john@example.com");

        PatientDetails patient2 = new PatientDetails();
        patient2.setId(2L);
        patient2.setFirstname("Jane Doe");
        patient2.setEmail("jane@example.com");

        List<PatientDetails> patientDetailsList = Arrays.asList(patient1, patient2);

        when(service.getPatientDetails()).thenReturn(patientDetailsList);

        // Act
        List<PatientDetails> result = controller.getPatientsDetailsC();

        // Assert
        assertEquals(patientDetailsList, result);
    }

    @Test
    void testSavePatientDetails_NullInput() {
        // Arrange
        when(service.savePatientDetails(null)).thenReturn(null);

        // Act
        PatientDetails result = controller.savePatientDetailsC(null);

        // Assert
        assertNull(result);
    }

    @Test
    void testGetPatientsDetails_EmptyList() {
        // Arrange
        when(service.getPatientDetails()).thenReturn(Arrays.asList());

        // Act
        List<PatientDetails> result = controller.getPatientsDetailsC();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSavePatientDetails_ServiceException() {
        // Arrange
        PatientDetails patientDetails = new PatientDetails();
        patientDetails.setId(1L);
        patientDetails.setFirstname("John Doe");
        patientDetails.setEmail("john@example.com");

        when(service.savePatientDetails(patientDetails)).thenThrow(new RuntimeException("Service Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.savePatientDetailsC(patientDetails));
    }

    @Test
    void testGetPatientsDetails_ServiceException() {
        // Arrange
        when(service.getPatientDetails()).thenThrow(new RuntimeException("Service Exception"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> controller.getPatientsDetailsC());
    }

    // Add more tests as needed for other methods in the controller class.
}
