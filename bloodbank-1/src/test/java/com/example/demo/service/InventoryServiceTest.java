package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.entity.Inventory;
import com.example.demo.repository.InventoryRepository;
import com.example.demo.service.InventoryService;

public class InventoryServiceTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @InjectMocks
    private InventoryService inventoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Add common setup tasks here, such as initializing mock behaviors
        // Mock repository behavior for common methods used in multiple tests
        List<Inventory> inventoryList = new ArrayList<>();

        Inventory inventory1 = new Inventory();
        inventory1.setBloodId(1);
        inventory1.setBloodGroup("A+");
        inventory1.setQuantity(5);

        Inventory inventory2 = new Inventory();
        inventory2.setBloodId(2);
        inventory2.setBloodGroup("B-");
        inventory2.setQuantity(3);

        inventoryList.add(inventory1);
        inventoryList.add(inventory2);

        when(inventoryRepository.findAll()).thenReturn(inventoryList);
        when(inventoryRepository.save(any(Inventory.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(inventoryRepository.findByBloodGroup(anyString())).thenReturn(List.of(inventory1));
       // when(inventoryRepository.getBloodCount(anyString())).thenReturn(1);
        System.out.println("Inventory List: " + inventoryList);

    }
    
    @Test
    public void testSaveInventory() {
        // Arrange
        Inventory inventoryToSave = new Inventory();
        inventoryToSave.setBloodId(1);
        inventoryToSave.setBloodGroup("A+");
        inventoryToSave.setQuantity(5);

        // Act
        inventoryService.saveInventory(inventoryToSave);

        // Assert
        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    public void testGetInventoryDetails() {
        // Act
        List<Inventory> resultInventoryList = inventoryService.getInventoryDetails();

        // Assert
        assertEquals(2, resultInventoryList.size());
        // Add more assertions if needed
    }

    @Test
    public void testUpdateStatus() {
        // Arrange
        Inventory inventoryToUpdate = new Inventory();
        inventoryToUpdate.setBloodId(1);

        // Act
        Inventory updatedInventory = inventoryService.updateStatus(inventoryToUpdate);

        // Assert
        assertEquals(inventoryToUpdate.getBloodId(), updatedInventory.getBloodId());
        // Add more assertions if needed
    }

    @Test
    public void testDeleteInventory() {
        // Arrange
        Inventory inventoryToDelete = new Inventory();
        inventoryToDelete.setBloodId(1);

        // Act
        inventoryService.deleteInventory(inventoryToDelete);

        // Assert
        verify(inventoryRepository, times(1)).delete(inventoryToDelete);
    }

    @Test
    public void testFindByBloodGroup() {
        // Arrange
        String bloodGroup = "A+";

        // Act
        List<Inventory> resultInventoryList = inventoryService.findByBloodGroup(bloodGroup);

        // Assert
        assertEquals(1, resultInventoryList.size());
        assertEquals(bloodGroup, resultInventoryList.get(0).getBloodGroup());
        // Add more assertions if needed
    }

    @Test
    public void testGetBloodCount() {
        // Arrange
        String bloodGroup = "B-";

        // Act
        int resultCount = inventoryService.getBloodCount(bloodGroup);

        // Assert
        assertEquals(1, resultCount);
    }
    
    


    // Add more test cases as needed for other methods
}
