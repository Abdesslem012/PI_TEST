/*package com.example.demo;

import com.example.demo.DTO.SectorDTO;
import com.example.demo.Entity.Sector;
import com.example.demo.Repository.ProgrammeRepository;
import com.example.demo.Repository.SectorRepository;
import com.example.demo.Repository.SpecializationRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.SectorService;
import com.example.demo.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

 class SectorServiceTest {

    @InjectMocks
    private SectorService sectorService;

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProgrammeRepository programmeRepository;

    @Mock
    private ResourceLoader resourceLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void testFindAll() {
        List<Sector> sectorList = new ArrayList<>();
        Sector sector1 = new Sector();
        sector1.setSectorId(1L);
        sector1.setName("IT");
        sector1.setDescription("Information Technology");
        sectorList.add(sector1);

        when(sectorRepository.findAll(any(Sort.class))).thenReturn(sectorList);

        List<SectorDTO> result = sectorService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("IT", result.get(0).getName());
    }

    @Test
     void testGetSector() {
        Sector sector = new Sector();
        sector.setSectorId(1L);
        sector.setName("Engineering");

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));

        SectorDTO result = sectorService.get(1L);

        assertNotNull(result);
        assertEquals("Engineering", result.getName());
    }

    @Test
     void testGetSectorNotFound() {
        when(sectorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            sectorService.get(1L);
        });
    }

    @Test
     void testCreateSector() {
        SectorDTO sectorDTO = new SectorDTO();
        sectorDTO.setName("Health");
        sectorDTO.setDescription("Healthcare sector");

        Sector sector = new Sector();
        sector.setSectorId(1L);
        sector.setName("Health");

        when(sectorRepository.save(any(Sector.class))).thenReturn(sector);

        Long sectorId = sectorService.create(sectorDTO);

        assertNotNull(sectorId);
        assertEquals(1L, sectorId);
    }

    @Test
     void testUpdateSector() {
        SectorDTO sectorDTO = new SectorDTO();
        sectorDTO.setName("Finance");
        sectorDTO.setDescription("Finance Sector");

        Sector sector = new Sector();
        sector.setSectorId(1L);
        sector.setName("Finance");

        when(sectorRepository.findById(1L)).thenReturn(Optional.of(sector));
        when(sectorRepository.save(any(Sector.class))).thenReturn(sector);

        sectorService.update(1L, sectorDTO);

        verify(sectorRepository, times(1)).save(sector);
        assertEquals("Finance", sector.getName());
    }

    @Test
     void testDeleteSector() {
        sectorService.delete(1L);
        verify(sectorRepository, times(1)).deleteById(1L);
    }

    @Test
     void testSpecializationIdExists() {
        when(sectorRepository.existsBySpecializationIdSpecializationId(1L)).thenReturn(true);
        boolean exists = sectorService.specializationIdExists(1L);
        assertTrue(exists);
    }

}
*/