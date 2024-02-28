	package com.example.demo.repository;
	import static org.junit.jupiter.api.Assertions.assertEquals;
	import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

	import org.junit.jupiter.api.BeforeEach;
	import org.junit.jupiter.api.Test;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
	import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

	import com.example.demo.entity.RegistrationDetails;
	import com.example.demo.repository.RegistrationDetailsRepository;

	@DataJpaTest
	@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
	public class RegistrationDetailsRepositoryTest {

	    @Autowired
	    private RegistrationDetailsRepository registrationDetailsRepository;

	    private RegistrationDetails adminRegistrationDetails;
	    private RegistrationDetails userRegistrationDetails;

	    @BeforeEach
	    public void setUp() {
	        // Common setup for each test
	        adminRegistrationDetails = new RegistrationDetails();
	        adminRegistrationDetails.setRole("ADMIN");
	        registrationDetailsRepository.save(adminRegistrationDetails);

	        userRegistrationDetails = new RegistrationDetails();
	        userRegistrationDetails.setRole("USER");
	        registrationDetailsRepository.save(userRegistrationDetails);
	    }

	    @Test
	    public void testFindByEmail() {
	        // Arrange
	        RegistrationDetails registrationDetails = new RegistrationDetails();
	        registrationDetails.setEmail("test@example.com");
	        registrationDetailsRepository.save(registrationDetails);

	        // Act
	        RegistrationDetails foundDetails = registrationDetailsRepository.findByEmail("test@example.com");

	        // Assert
	        assertNotNull(foundDetails);
	        assertEquals("test@example.com", foundDetails.getEmail());
	    }

	    @Test
	    public void testFindAllByEmail() {
	        // Act
	    	 RegistrationDetails registrationDetails = new RegistrationDetails();
		        registrationDetails.setEmail("test1@example.com");
		        registrationDetails.setId(560);
		        registrationDetailsRepository.save(registrationDetails);
		
	        List<RegistrationDetails> foundDetailsList = registrationDetailsRepository.findAllByEmail("test1@example.com");

	        // Assert
	        assertNotNull(foundDetailsList);
	        assertEquals(1, foundDetailsList.size()); // Assuming "test1@example.com" doesn't exist in the setup
	    }

	    @Test
	    public void testFindByRole() {
	        // Act
	        List<RegistrationDetails> foundDetailsList = registrationDetailsRepository.findByRole("ADMIN");

	        // Assert
	        assertNotNull(foundDetailsList);
	        assertEquals(2, foundDetailsList.size());
	        assertEquals("ADMIN", foundDetailsList.get(0).getRole());
	    }
	    
	   

	    @Test
	    public void testFindByEmail_NonExistingEmail() {
	        // Act
	        RegistrationDetails foundDetails = registrationDetailsRepository.findByEmail("nonexistent@example.com");

	        // Assert
	        assertNull(foundDetails);
	    }

	   

	    @Test
	    public void testFindAllByEmail_NonExistingEmail() {
	        // Act
	        List<RegistrationDetails> foundDetailsList = registrationDetailsRepository.findAllByEmail("nonexistent@example.com");

	        // Assert
	        assertNotNull(foundDetailsList);
	        assertEquals(0, foundDetailsList.size());
	    }

	   

	    @Test
	    public void testFindByRole_NonExistingRole() {
	        // Act
	        List<RegistrationDetails> foundDetailsList = registrationDetailsRepository.findByRole("NONEXISTENT_ROLE");

	        // Assert
	        assertNotNull(foundDetailsList);
	        assertEquals(0, foundDetailsList.size());
	    }
	}

	
	
	
	


