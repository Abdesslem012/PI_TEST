package com.example.demo.Service;

import com.example.demo.DTO.SectorDTO;
import com.example.demo.Entity.Programme;
import com.example.demo.Entity.Sector;
import com.example.demo.Entity.Specialization;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.ProgrammeRepository;
import com.example.demo.Repository.SectorRepository;
import com.example.demo.Repository.SpecializationRepository;
import com.example.demo.Repository.StudentRepository;
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
public class SectorService {

    private final SectorRepository sectorRepository;
    private final SpecializationRepository specializationRepository;
    private final StudentRepository studentRepository;
    private final ProgrammeRepository programmeRepository;

    private  final ResourceLoader resourceLoader;

    private SpecializationService specializationService;

    public SectorService(final SectorRepository sectorRepository,
                         final SpecializationRepository specializationRepository,
                         final StudentRepository studentRepository,
                         final ProgrammeRepository programmeRepository, @Qualifier("") ResourceLoader resourceLoader) {
        this.sectorRepository = sectorRepository;
        this.specializationRepository = specializationRepository;
        this.studentRepository = studentRepository;
        this.programmeRepository = programmeRepository;
        this.resourceLoader = resourceLoader;
    }

    public List<SectorDTO> findAll() {
        final List<Sector> sectors = sectorRepository.findAll(Sort.by("sectorId"));
        return sectors.stream()
                .map(sector -> mapToDTO(sector, new SectorDTO()))
                .toList();
    }

    public SectorDTO get(final Long sectorId) {
        return sectorRepository.findById(sectorId)
                .map(sector -> mapToDTO(sector, new SectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final SectorDTO sectorDTO) {
        final Sector sector = new Sector();
        mapToEntity(sectorDTO, sector);
        return sectorRepository.save(sector).getSectorId();
    }

    public void update(final Long sectorId, final SectorDTO sectorDTO) {
        final Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(sectorDTO, sector);
        sectorRepository.save(sector);
    }

    public void delete(final Long sectorId) {
        sectorRepository.deleteById(sectorId);
    }

    private SectorDTO mapToDTO(final Sector sector, final SectorDTO sectorDTO) {
        sectorDTO.setSectorId(sector.getSectorId());
        sectorDTO.setName(sector.getName());
        sectorDTO.setDescription(sector.getDescription());
        sectorDTO.setSpecializationId(sector.getSpecializationId() == null ? null : (sector.getSpecializationId().getSpecializationId()));
        return sectorDTO;
    }

    private Sector mapToEntity(final SectorDTO sectorDTO, final Sector sector) {
        sector.setName(sectorDTO.getName());
        sector.setDescription(sectorDTO.getDescription());
        final Specialization specializationId = sectorDTO.getSpecializationId() == null ? null : specializationRepository.findById(sectorDTO.getSpecializationId())
                .orElseThrow(() -> new NotFoundException("specializationId not found"));
        sector.setSpecializationId(specializationId);
        return sector;
    }

    public boolean specializationIdExists(final Long specializationId) {
        return sectorRepository.existsBySpecializationIdSpecializationId(specializationId);
    }

    public ReferencedWarning getReferencedWarning(final Long sectorId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Sector sector = sectorRepository.findById(sectorId)
                .orElseThrow(NotFoundException::new);
        final Student sectorIdStudent = studentRepository.findFirstBySectorId(sector);
        if (sectorIdStudent != null) {
            referencedWarning.setKey("sector.student.sectorId.referenced");
            referencedWarning.addParam(sectorIdStudent.getStudentId());
            return referencedWarning;
        }
        final Programme sectorIdProgramme = programmeRepository.findFirstBySectorId(sector);
        if (sectorIdProgramme != null) {
            referencedWarning.setKey("sector.programme.sectorId.referenced");
            referencedWarning.addParam(sectorIdProgramme.getProgrammeId());
            return referencedWarning;
        }
        return null;
    }

     public Sector findById(Long sectorId) {
        return  sectorRepository.findById(sectorId).orElse(null);
    }

    public void insertSectorsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:sectors.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {});

        List<Sector> sectors = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("sectors".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    SectorDTO sectorDTO = objectMapper.convertValue(obj, SectorDTO.class);

                    // Vérifiez que specializationService n'est pas null
                    /*if (specializationService != null) {
                        Specialization specialization = specializationService.findById(sectorDTO.getSpecializationId());
                        if (specialization != null) {
                            sectorDTO.setSpecializationId(specialization.getSpecializationId());*/

                            // Convert SectorDTO to Sector and add to the list
                            Sector sector = convertToSector(sectorDTO);
                            sectors.add(sector);
                        /*} else {
                            // Gérer le cas où la spécialisation n'est pas trouvée
                            System.err.println("Spécialisation non trouvée pour l'ID : " + sectorDTO.getSpecializationId());
                        }
                    } else {
                        // Gérer le cas où specializationService est null
                        throw new IllegalStateException("Le service de spécialisation est null. Vérifiez la configuration de votre application.");
                    }*/
                }
            }
        }
        // Enregistrez la liste des objets Sector dans la base de données
        sectorRepository.saveAll(sectors);
    }

    private Sector convertToSector(SectorDTO sectorDTO) {
        Sector sector = new Sector();
        sector.setSectorId(sectorDTO.getSectorId());
        sector.setName(sectorDTO.getName());
        sector.setDescription(sectorDTO.getDescription());
        Specialization specialization = sector.getSpecializationId();
        // Set the specialization for the sector
        sector.setSpecializationId(specialization);

        return sector;
    }



}
