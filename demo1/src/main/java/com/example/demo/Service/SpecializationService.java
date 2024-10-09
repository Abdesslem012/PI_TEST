package com.example.demo.Service;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.example.demo.DTO.SpecializationDTO;
import com.example.demo.Entity.Specialization;
import com.example.demo.Repository.SectorRepository;
import com.example.demo.Repository.SpecializationRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;


@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final SectorRepository sectorRepository;

    private  final ResourceLoader resourceLoader;
    private final MongoTemplate mongoTemplate;

    private final SectorService sectorService;


    public SpecializationService(final SpecializationRepository specializationRepository,
                                 final SectorRepository sectorRepository, @Qualifier("") ResourceLoader resourceLoader, MongoTemplate mongoTemplate, SectorService sectorService) {
        this.specializationRepository = specializationRepository;
        this.sectorRepository = sectorRepository;
        this.resourceLoader = resourceLoader;
        this.mongoTemplate = mongoTemplate;
        this.sectorService = sectorService;
    }

      /*public void insertSpecializationsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:specialities.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {});

        List<Specialization> specializations = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("specialites".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    SpecializationDTO specializationDTO = objectMapper.convertValue(obj, SpecializationDTO.class);

                    // Fetch Sector object based on specializationDTO.getSectorIds()
                    Sector sector = sectorService.findById(((Sector) specializationDTO.getSectorIds()).getSectorId());

                    // Set the Sector object in the SpecializationDTO
                    specializationDTO.setSectorIds((Set<Sector>) sector);

                    // Convert SpecializationDTO to Specialization and add to the list
                    Specialization specialization = convertToSpecialization(specializationDTO);
                    specializations.add(specialization);
                }
            }
        }

        // Save the list of Specialization objects to the database
        specializationRepository.saveAll(specializations);
    }*/


    public void insertSpecializationsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:specialities.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {
        });

        List<Specialization> specializations = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("specialites".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    SpecializationDTO specializationDTO = objectMapper.convertValue(obj, SpecializationDTO.class);
                    // Convert SectorDTO to Sector and add to the list
                    Specialization specialization = convertToSpecialization(specializationDTO);
                    specializations.add(specialization);
                }
            }
        }
        // Enregistrez la liste des objets Sector dans la base de données
        specializationRepository.saveAll(specializations);
    }
    private Specialization convertToSpecialization(SpecializationDTO specializationDTO) {
        Specialization specialization = new Specialization();
        specialization.setSpecializationId(specializationDTO.getSpecializationId());
        specialization.setName(specializationDTO.getName());
        specialization.setDescription(specializationDTO.getDescription());
        specialization.setSectorIds(specializationDTO.getSectorIds());
        return specialization;
    }

    public List<SpecializationDTO> findAll() {
        final List<Specialization> specializations = specializationRepository.findAll(Sort.by("specializationId"));
        return specializations.stream()
                .map(specialization -> mapToDTO(specialization, new SpecializationDTO()))
                .toList();
    }

    public SpecializationDTO get(final Long specializationId) {
        return specializationRepository.findById(specializationId)
                .map(specialization -> mapToDTO(specialization, new SpecializationDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SpecializationDTO specializationDTO) {
        final Specialization specialization = new Specialization();
        mapToEntity(specializationDTO, specialization);
        return specializationRepository.save(specialization).getSpecializationId();
    }

    public void update(final Long specializationId, final SpecializationDTO specializationDTO) {
        final Specialization specialization = specializationRepository.findById(specializationId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(specializationDTO, specialization);
        specializationRepository.save(specialization);
    }

    public void delete(final Long specializationId) {
        specializationRepository.deleteById(specializationId);
    }

    private SpecializationDTO mapToDTO(final Specialization specialization,
            final SpecializationDTO specializationDTO) {
        specializationDTO.setSpecializationId(specialization.getSpecializationId());
        specializationDTO.setName(specialization.getName());
        specializationDTO.setDescription(specialization.getDescription());
        specializationDTO.setSectorIds(specialization.getSectorIds());
        return specializationDTO;
    }

    private Specialization mapToEntity(final SpecializationDTO specializationDTO,
            final Specialization specialization) {
        specialization.setName(specializationDTO.getName());
        specialization.setDescription(specializationDTO.getDescription());
        specialization.setSectorIds(specializationDTO.getSectorIds());
        return specialization;
    }


    public ReferencedWarning getReferencedWarning(Long specializationId) {
        return null;
    }

    public Specialization findById(Long specializationId) {
        Optional<Specialization> optionalSpecialization = specializationRepository.findById(specializationId);
        return optionalSpecialization.orElse(null);
    }
}
