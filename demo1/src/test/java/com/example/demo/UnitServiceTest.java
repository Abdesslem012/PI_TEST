package com.example.demo;

import com.example.demo.DTO.UnitDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Unit;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.TeacherRepository;
import com.example.demo.Repository.UnitRepository;
import com.example.demo.Service.UnitService;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UnitServiceTest {

    @Mock
    private UnitRepository unitRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private LessonRepository lessonRepository;

    @InjectMocks
    private UnitService unitService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findAll_ShouldReturnUnitDTOList() {
        // Arrange
        Unit unit1 = new Unit();
        unit1.setUnitId(1L);
        unit1.setName("Unit 1");

        Unit unit2 = new Unit();
        unit2.setUnitId(2L);
        unit2.setName("Unit 2");

        when(unitRepository.findAll(Sort.by("unitId"))).thenReturn(List.of(unit1, unit2));

        // Act
        List<UnitDTO> result = unitService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getUnitId());
        assertEquals("Unit 1", result.get(0).getName());
        assertEquals(2L, result.get(1).getUnitId());
        assertEquals("Unit 2", result.get(1).getName());

        verify(unitRepository, times(1)).findAll(Sort.by("unitId"));
    }

    @Test
    void get_ShouldReturnUnitDTO_WhenUnitExists() {
        // Arrange
        Unit unit = new Unit();
        unit.setUnitId(1L);
        unit.setName("Unit 1");

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));

        // Act
        UnitDTO result = unitService.get(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getUnitId());
        assertEquals("Unit 1", result.getName());

        verify(unitRepository, times(1)).findById(1L);
    }

    @Test
    void get_ShouldThrowNotFoundException_WhenUnitDoesNotExist() {
        // Arrange
        when(unitRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> unitService.get(1L));

        verify(unitRepository, times(1)).findById(1L);
    }

    @Test
    void create_ShouldReturnUnitId_WhenUnitIsCreated() {
        // Arrange
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setName("New Unit");

        Unit unit = new Unit();
        unit.setUnitId(1L);

        when(unitRepository.save(any(Unit.class))).thenReturn(unit);

        // Act
        Long result = unitService.create(unitDTO);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result);

        verify(unitRepository, times(1)).save(any(Unit.class));
    }

    @Test
    void update_ShouldUpdateUnit_WhenUnitExists() {
        // Arrange
        UnitDTO unitDTO = new UnitDTO();
        unitDTO.setName("Updated Unit");

        Unit unit = new Unit();
        unit.setUnitId(1L);

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));

        // Act
        unitService.update(1L, unitDTO);

        // Assert
        verify(unitRepository, times(1)).findById(1L);
        verify(unitRepository, times(1)).save(unit);
    }

    @Test
    void delete_ShouldDeleteUnit_WhenUnitExists() {
        // Act
        unitService.delete(1L);

        // Assert
        verify(unitRepository, times(1)).deleteById(1L);
    }

    @Test
    void getReferencedWarning_ShouldReturnWarning_WhenTeacherOrLessonIsReferenced() {
        // Arrange
        Unit unit = new Unit();
        unit.setUnitId(1L);

        Teacher teacher = new Teacher();
        teacher.setTeacherId(10L);

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(teacherRepository.findFirstByUnitId(unit)).thenReturn(teacher);

        // Act
        ReferencedWarning result = unitService.getReferencedWarning(1L);

        // Assert
        assertNotNull(result);
        assertEquals("unit.teacher.unitId.referenced", result.getKey());
        assertEquals(10L, result.getParams().get(0));

        verify(unitRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findFirstByUnitId(unit);
    }

    @Test
    void getReferencedWarning_ShouldReturnWarning_WhenLessonIsReferenced() {
        // Arrange
        Unit unit = new Unit();
        unit.setUnitId(1L);

        Lesson lesson = new Lesson();
        lesson.setLessonId(20L);

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(teacherRepository.findFirstByUnitId(unit)).thenReturn(null);
        when(lessonRepository.findFirstByUnitId(unit)).thenReturn(lesson);

        // Act
        ReferencedWarning result = unitService.getReferencedWarning(1L);

        // Assert
        assertNotNull(result);
        assertEquals("unit.lesson.unitId.referenced", result.getKey());
        assertEquals(20L, result.getParams().get(0));

        verify(unitRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findFirstByUnitId(unit);
        verify(lessonRepository, times(1)).findFirstByUnitId(unit);
    }

    @Test
    void getReferencedWarning_ShouldReturnNull_WhenNoReferencesExist() {
        // Arrange
        Unit unit = new Unit();
        unit.setUnitId(1L);

        when(unitRepository.findById(1L)).thenReturn(Optional.of(unit));
        when(teacherRepository.findFirstByUnitId(unit)).thenReturn(null);
        when(lessonRepository.findFirstByUnitId(unit)).thenReturn(null);

        // Act
        ReferencedWarning result = unitService.getReferencedWarning(1L);

        // Assert
        assertNull(result);

        verify(unitRepository, times(1)).findById(1L);
        verify(teacherRepository, times(1)).findFirstByUnitId(unit);
        verify(lessonRepository, times(1)).findFirstByUnitId(unit);
    }
}
