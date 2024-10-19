package com.example.demo;

import com.example.demo.DTO.SectorDTO;
import com.example.demo.Entity.Sector;
import com.example.demo.Entity.Specialization;
import com.example.demo.Repository.SectorRepository;
import com.example.demo.Repository.SpecializationRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.ProgrammeRepository;
import com.example.demo.Service.SectorService;
import com.example.demo.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SectorServiceTest {

    @Mock
    private SectorRepository sectorRepository;

    @Mock
    private SpecializationRepository specializationRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private ProgrammeRepository programmeRepository;

    @InjectMocks
    private SectorService sectorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }



}
