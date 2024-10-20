package com.example.demo;

import com.example.demo.Entity.Sector;
import com.example.demo.Repository.SectorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
class SectorFunctionalTest {

   @Autowired
   private MockMvc mockMvc;

   @Autowired
   private SectorRepository sectorRepository;

   @BeforeEach
   void setUp() {
      sectorRepository.deleteAll(); // Clear the repository before each test
   }

   @Test
   void testGetAllSectors() throws Exception {
      // Create a new Sector instance
      Sector sector = new Sector();
      sector.setName("IT"); // Set a valid name
      sector.setDescription("Information Technology"); // Set a valid description

      // Save the Sector instance
      sectorRepository.save(sector);

      // Perform the request and verify the result
      mockMvc.perform(get("/api/sectors")
                      .contentType(MediaType.APPLICATION_JSON))
              .andExpect(status().isOk());
   }

   // Add more test methods for other functionality as needed
   // For example, testing creating a new sector
   @Test
   public void testCreateUnit() throws Exception {
      MockMultipartFile contentFile = new MockMultipartFile("content", "test.pdf", "application/pdf", "test content".getBytes());

      mockMvc.perform(multipart("/api/units")
                      .file(contentFile)
                      .param("name", "New Unit")
                      .param("description", "New Unit Description"))
              .andExpect(status().isCreated());
   }

}