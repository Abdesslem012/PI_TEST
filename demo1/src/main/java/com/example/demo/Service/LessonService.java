package com.example.demo.Service;



import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo.DTO.LessonDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Rating;
import com.example.demo.Entity.Ressources;
import com.example.demo.Entity.Unit;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.RatingRepository;
import com.example.demo.Repository.RessourcesRepository;
import com.example.demo.Repository.UnitRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LessonService {

    private final LessonRepository lessonRepository;
    private final UnitRepository unitRepository;
    private final RessourcesRepository ressourcesRepository;

    private final RessourcesService ressourcesService;

    private final RessourcesRepository ressourceRepository;

    private final RatingRepository ratingRepository;

    public LessonService(final LessonRepository lessonRepository,
                         final UnitRepository unitRepository, final RessourcesRepository ressourcesRepository, RessourcesService ressourcesService, RessourcesRepository ressourceRepository, RatingRepository ratingRepository) {
        this.lessonRepository = lessonRepository;
        this.unitRepository = unitRepository;
        this.ressourcesRepository = ressourcesRepository;
        this.ressourcesService = ressourcesService;
        this.ressourceRepository = ressourceRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<LessonDTO> findAll() {
        final List<Lesson> lessons = lessonRepository.findAll(Sort.by("lessonId"));
        return lessons.stream()
                .map(lesson -> mapToDTO(lesson, new LessonDTO()))
                .toList();
    }

    public LessonDTO get(final Long lessonId) {
        return lessonRepository.findById(lessonId)
                .map(lesson -> mapToDTO(lesson, new LessonDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LessonDTO lessonDTO) {
        final Lesson lesson = new Lesson();
        mapToEntity(lessonDTO, lesson);
        return lessonRepository.save(lesson).getLessonId();
    }

    public void update(final Long lessonId, final LessonDTO lessonDTO) {
        final Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(lessonDTO, lesson);
        lessonRepository.save(lesson);
    }

    public void delete(final Long lessonId) {
        lessonRepository.deleteById(lessonId);
    }

    private LessonDTO mapToDTO(final Lesson lesson, final LessonDTO lessonDTO) {
        lessonDTO.setLessonId(lesson.getLessonId());
        lessonDTO.setDescription(lesson.getDescription());
        lessonDTO.setUnitId(lesson.getUnitId() == null ? null : lesson.getUnitId().getUnitId());
        Set<Long> ressourcesIds = lesson.getRessourcess().stream()
                .map(Ressources::getRessourcesId) // Supposons que getRessourcesId() retourne l'ID de l'objet Ressources
                .collect(Collectors.toSet());

        lessonDTO.setRessourcess(ressourcesIds);
        return lessonDTO;
    }

    private Lesson mapToEntity(final LessonDTO lessonDTO, final Lesson lesson) {
        lesson.setLessonId(lessonDTO.getLessonId());
        lesson.setDescription(lessonDTO.getDescription());
        final Unit unitId = lessonDTO.getUnitId() == null ? null : unitRepository.findById(lessonDTO.getUnitId())
                .orElseThrow(() -> new NotFoundException("unitId not found"));
        lesson.setUnitId(unitId);
        if (lessonDTO.getRessourcess() != null) {
            Set<Ressources> ressources = lessonDTO.getRessourcess().stream()
                    .map(id -> ressourcesService.findById(id)
                            .orElseThrow(() -> new NotFoundException("Ressources not found with ID: " + id)))
                    .collect(Collectors.toSet());
            lesson.setRessourcess(ressources);
        }
        return lesson;
    }

    public ReferencedWarning getReferencedWarning(final Long lessonId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(NotFoundException::new);
        final Ressources lessonIdRessources = ressourcesRepository.findFirstByLessonId(lesson);
        if (lessonIdRessources != null) {
            referencedWarning.setKey("lesson.ressources.lessonId.referenced");
            referencedWarning.addParam(lessonIdRessources.getRessourcesId());
            return referencedWarning;
        }
        return null;
    }

    public void assignRessourceToLesson(String lessonDescription, List<String> ressourceUrls) {
        Lesson lesson = findByDescription(lessonDescription)
                .orElseThrow(() -> new RuntimeException("Lesson not found with description: " + lessonDescription));
        ressourceUrls.forEach(ressourceUrl -> {
            List<Ressources> ressources = ressourceRepository.findByUrl(ressourceUrl);
            ressources.forEach(ressource -> {
                ressource.setLesson(lesson); // Assurez-vous d'avoir une méthode setQuiz dans Question
                ressourcesRepository.save(ressource);
            });
        });
    }

    public void assignRatingToLesson(String lessonDescription, String ratingNom) {
        Lesson lesson = lessonRepository.findByDescription(lessonDescription)
                .orElseThrow(() -> new RuntimeException("Lesson not found with description: " + lessonDescription));

        Rating rating = ratingRepository.findByNom(ratingNom)
                .orElseThrow(() -> new RuntimeException("Rating not found with name: " + ratingNom));

        lesson.setRatingId(rating);
        lessonRepository.save(lesson);
    }



    public Optional<Lesson> findByDescription(String description) {
        try {
            return lessonRepository.findByDescription(description);
        } catch (IncorrectResultSizeDataAccessException ex) {
            throw new RuntimeException("Plusieurs leçons trouvées avec la description: " + description);
        }
    }
}
