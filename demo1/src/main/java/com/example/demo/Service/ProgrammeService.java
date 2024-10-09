package com.example.demo.Service;

import com.example.demo.DTO.ProgrammeDTO;
import com.example.demo.Entity.Programme;
import com.example.demo.Entity.Sector;
import com.example.demo.Repository.ProgrammeRepository;
import com.example.demo.Repository.SectorRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProgrammeService {

    private final ProgrammeRepository programmeRepository;
    private final SectorRepository sectorRepository;

    private final ResourceLoader resourceLoader;

    public ProgrammeService(final ProgrammeRepository programmeRepository,
                            final SectorRepository sectorRepository, @Qualifier("") ResourceLoader resourceLoader) {
        this.programmeRepository = programmeRepository;
        this.sectorRepository = sectorRepository;
        this.resourceLoader = resourceLoader;
    }


    public void insertProgrammesFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:programmes.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {
        });

        List<Programme> programmes = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("programmes".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    ProgrammeDTO programmeDTO = objectMapper.convertValue(obj, ProgrammeDTO.class);

                    // Convert SectorDTO to Sector and add to the list
                    Programme programme = convertToProgramme(programmeDTO);
                    programmes.add(programme);
                }
            }
        }
        // Enregistrez la liste des objets Sector dans la base de données
        programmeRepository.saveAll(programmes);
    }

    private Programme convertToProgramme(ProgrammeDTO programmeDTO) {
        Programme programme = new Programme();
        programme.setProgrammeId(programmeDTO.getProgrammeId());
        programme.setName(programmeDTO.getName());
        programme.setDescription(programmeDTO.getDescription());
        programme.setStartDate(programmeDTO.getStartDate());
        programme.setEndDate(programmeDTO.getEndDate());
        final Sector sectorId = programmeDTO.getSectorId() == null ? null : sectorRepository.findById(programmeDTO.getSectorId())
                .orElseThrow(() -> new NotFoundException("sectorId not found"));
        programme.setSectorId(sectorId);
        //programme.setSectorId(programmeDTO.getSectorId());
        return programme;
    }

    public List<ProgrammeDTO> findAll() {
        final List<Programme> programmes = programmeRepository.findAll(Sort.by("programmeId"));
        return programmes.stream()
                .map(programme -> mapToDTO(programme, new ProgrammeDTO()))
                .toList();
    }

    public ProgrammeDTO get(final Long programmeId) {
        return programmeRepository.findById(programmeId)
                .map(programme -> mapToDTO(programme, new ProgrammeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ProgrammeDTO programmeDTO) {
        final Programme programme = new Programme();
        mapToEntity(programmeDTO, programme);
        return programmeRepository.save(programme).getProgrammeId();
    }

    public void update(final Long programmeId, final ProgrammeDTO programmeDTO) {
        final Programme programme = programmeRepository.findById(programmeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(programmeDTO, programme);
        programmeRepository.save(programme);
    }

    public void delete(final Long programmeId) {
        programmeRepository.deleteById(programmeId);
    }

    private ProgrammeDTO mapToDTO(final Programme programme, final ProgrammeDTO programmeDTO) {
        programmeDTO.setProgrammeId(programme.getProgrammeId());
        programmeDTO.setName(programme.getName());
        programmeDTO.setDescription(programme.getDescription());
        programmeDTO.setStartDate(programme.getStartDate());
        programmeDTO.setEndDate(programme.getEndDate());
        programmeDTO.setSectorId(programme.getSectorId() == null ? null : programme.getSectorId().getSectorId());
        return programmeDTO;
    }

    private Programme mapToEntity(final ProgrammeDTO programmeDTO, final Programme programme) {
        programme.setName(programmeDTO.getName());
        programme.setDescription(programmeDTO.getDescription());
        programme.setStartDate(programmeDTO.getStartDate());
        programme.setEndDate(programmeDTO.getEndDate());
        final Sector sectorId = programmeDTO.getSectorId() == null ? null : sectorRepository.findById(programmeDTO.getSectorId())
                .orElseThrow(() -> new NotFoundException("sectorId not found"));
        programme.setSectorId(sectorId);
        return programme;
    }

    public ReferencedWarning getReferencedWarning(final Long programmeId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Programme programme = programmeRepository.findById(programmeId)
                .orElseThrow(NotFoundException::new);
        return null;
    }

}
