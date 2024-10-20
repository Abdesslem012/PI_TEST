package com.example.demo;

/*import com.example.demo.Entity.Unit;
import com.example.demo.Repository.UnitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UnitFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UnitRepository unitRepository;

    @BeforeEach
    void setUp() {
        unitRepository.deleteAll(); // Clean the repository before each test
    }

    @Test
    void testGetAllUnits() throws Exception {
        // Create a new instance of Unit
        Unit unit1 = new Unit();
        unit1.setName("Unit 1");
        unit1.setDescription("Description for Unit 1");
        unit1.setContent("Content for Unit 1");

        Unit unit2 = new Unit();
        unit2.setName("Unit 2");
        unit2.setDescription("Description for Unit 2");
        unit2.setContent("Content for Unit 2");

        // Save the units
        unitRepository.save(unit1);
        unitRepository.save(unit2);

        // Perform the request and verify the result
        mockMvc.perform(get("/api/units")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // Other test methods can go here
}
*/