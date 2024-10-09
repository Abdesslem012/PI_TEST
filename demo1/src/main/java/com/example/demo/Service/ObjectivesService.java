package com.example.demo.Service;

import java.util.List;

import com.example.demo.DTO.ObjectivesDTO;
import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Entity.Objectives;
import com.example.demo.Repository.MoitoringAcadimicObjectivesRepository;
import com.example.demo.Repository.ObjectivesRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ObjectivesService {

    private final ObjectivesRepository objectivesRepository;
    private final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository;

    public ObjectivesService(final ObjectivesRepository objectivesRepository,
            final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository) {
        this.objectivesRepository = objectivesRepository;
        this.moitoringAcadimicObjectivesRepository = moitoringAcadimicObjectivesRepository;
    }

    public List<ObjectivesDTO> findAll() {
        final List<Objectives> objectiveses = objectivesRepository.findAll(Sort.by("objectivesId"));
        return objectiveses.stream()
                .map(objectives -> mapToDTO(objectives, new ObjectivesDTO()))
                .toList();
    }

    public ObjectivesDTO get(final Long objectivesId) {
        return objectivesRepository.findById(objectivesId)
                .map(objectives -> mapToDTO(objectives, new ObjectivesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final ObjectivesDTO objectivesDTO) {
        final Objectives objectives = new Objectives();
        mapToEntity(objectivesDTO, objectives);
        return objectivesRepository.save(objectives).getObjectivesId();
    }

    public void update(final Long objectivesId, final ObjectivesDTO objectivesDTO) {
        final Objectives objectives = objectivesRepository.findById(objectivesId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(objectivesDTO, objectives);
        objectivesRepository.save(objectives);
    }

    public void delete(final Long objectivesId) {
        objectivesRepository.deleteById(objectivesId);
    }

    private ObjectivesDTO mapToDTO(final Objectives objectives, final ObjectivesDTO objectivesDTO) {
        objectivesDTO.setObjectivesId(objectives.getObjectivesId());
        objectivesDTO.setDescription(objectives.getDescription());
       objectivesDTO.setObjectives(objectives.getObjectives() == null ? null : objectives.getObjectives().getMaoId());
        return objectivesDTO;
    }

    private Objectives mapToEntity(final ObjectivesDTO objectivesDTO, final Objectives objectives) {
        objectives.setDescription(objectivesDTO.getDescription());
        final MoitoringAcadimicObjectives objective = objectivesDTO.getObjectives() == null ? null : moitoringAcadimicObjectivesRepository.findById(Long.valueOf(String.valueOf(objectivesDTO.getObjectives())))
                .orElseThrow(() -> new NotFoundException("objectives not found"));
        objectives.setObjectives(objective);
        return objectives;
    }


}