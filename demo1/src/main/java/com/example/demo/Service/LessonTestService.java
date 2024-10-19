package com.example.demo.Service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Example;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LessonTestService {

    @InjectMocks
    private LessonService lessonService;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private RessourcesRepository ressourcesRepository;

    @Mock
    private RessourcesService ressourcesService;

    @Mock
    private RatingRepository ratingRepository;

    private Lesson lesson;
    private LessonDTO lessonDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Crée une leçon fictive
        lesson = new Lesson();
        lesson.setLessonId(1L);
        lesson.setDescription("Test Lesson");

        // Crée un DTO fictif pour la leçon
        lessonDTO = new LessonDTO();
        lessonDTO.setLessonId(1L);
        lessonDTO.setDescription("Test Lesson");
    }

    @Test
    void testGetLessonById_NotFound() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.get(1L));
    }

    @Test
    void testCreateLesson() {
        when(lessonRepository.save(any(Lesson.class))).thenReturn(lesson);
        var result = lessonService.create(lessonDTO);
        assertEquals(lesson.getLessonId(), result);
    }

    @Test
    void testUpdateLesson_Success() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.of(lesson));
        lessonService.update(1L, lessonDTO);
        verify(lessonRepository).save(any(Lesson.class));
    }

    @Test
    void testUpdateLesson_NotFound() {
        when(lessonRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> lessonService.update(1L, lessonDTO));
    }

    @Test
    void testDeleteLesson() {
        doNothing().when(lessonRepository).deleteById(anyLong());
        lessonService.delete(1L);
        verify(lessonRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAssignRessourceToLesson() {
        Ressources ressource = new Ressources();
        ressource.setRessourcesId(1L);
        ressource.setUrl("http://example.com");

        when(lessonRepository.findByDescription(anyString())).thenReturn(Optional.of(lesson));
        when(ressourcesRepository.findByUrl(anyString())).thenReturn(Collections.singletonList(ressource));

        lessonService.assignRessourceToLesson("Test Lesson", Collections.singletonList("http://example.com"));
        verify(ressourcesRepository, times(1)).save(any(Ressources.class));
    }

    @Test
    void testAssignRatingToLesson() {
        Rating rating = new Rating();
        rating.setNom("5 Stars");

        when(lessonRepository.findByDescription(anyString())).thenReturn(Optional.of(lesson));
        when(ratingRepository.findByNom(anyString())).thenReturn(Optional.of(rating));

        lessonService.assignRatingToLesson("Test Lesson", "5 Stars");
        verify(lessonRepository, times(1)).save(any(Lesson.class));
    }
}
