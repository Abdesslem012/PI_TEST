package com.example.demo.Service;


import java.util.List;
import java.util.Optional;

import com.example.demo.DTO.RessourcesDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Ressources;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.RessourcesRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class RessourcesService {

    private final RessourcesRepository ressourcesRepository;
    private final LessonRepository lessonRepository;

    public RessourcesService(final RessourcesRepository ressourcesRepository,
            final LessonRepository lessonRepository) {
        this.ressourcesRepository = ressourcesRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<RessourcesDTO> findAll() {
        final List<Ressources> ressourceses = ressourcesRepository.findAll(Sort.by("ressourcesId"));
        return ressourceses.stream()
                .map(ressources -> mapToDTO(ressources, new RessourcesDTO()))
                .toList();
    }

    public Ressources addRessourceToLesson(Ressources ressource, Long lessonId) {
        // Find the lesson by ID
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow(() ->
                new RuntimeException("Lesson not found"));

        // Set the lesson to the ressource and save it
        ressource.setLessonId(lesson);
        Ressources savedRessource = ressourcesRepository.save(ressource);

        // Add the ressource to the lesson's set of ressources and save the lesson
        lesson.getRessourcess().add(savedRessource);
        lessonRepository.save(lesson);

        return savedRessource;
    }

    public RessourcesDTO get(final Long ressourcesId) {
        return ressourcesRepository.findById(ressourcesId)
                .map(ressources -> mapToDTO(ressources, new RessourcesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final RessourcesDTO ressourcesDTO) {
        final Ressources ressources = new Ressources();
        mapToEntity(ressourcesDTO, ressources);
        return ressourcesRepository.save(ressources).getRessourcesId();
    }

    public void update(final Long ressourcesId, final RessourcesDTO ressourcesDTO) {
        final Ressources ressources = ressourcesRepository.findById(ressourcesId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(ressourcesDTO, ressources);
        ressourcesRepository.save(ressources);
    }

    public void delete(final Long ressourcesId) {
        ressourcesRepository.deleteById(ressourcesId);
    }

    private RessourcesDTO mapToDTO(final Ressources ressources, final RessourcesDTO ressourcesDTO) {
        ressourcesDTO.setRessourcesId(ressources.getRessourcesId());
        ressourcesDTO.setType(ressources.getType());
        ressourcesDTO.setUrl(ressources.getUrl());
        ressourcesDTO.setLessonId(ressources.getLessonId() == null ? null : ressources.getLessonId().getLessonId());
        return ressourcesDTO;
    }

    private Ressources mapToEntity(final RessourcesDTO ressourcesDTO, final Ressources ressources) {
        ressources.setRessourcesId(ressourcesDTO.getRessourcesId());
        ressources.setType(ressourcesDTO.getType());
        ressources.setUrl(ressourcesDTO.getUrl());
        final Lesson lessonId = ressourcesDTO.getLessonId() == null ? null : lessonRepository.findById(ressourcesDTO.getLessonId())
                .orElseThrow(() -> new NotFoundException("lessonId not found"));
        ressources.setLessonId(lessonId);
        return ressources;
    }

    public Optional<Ressources> findById(Long id) {
        return ressourcesRepository.findById(id);
    }
}
