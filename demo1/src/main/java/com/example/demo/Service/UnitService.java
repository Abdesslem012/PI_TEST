package com.example.demo.Service;


import java.util.List;

import com.example.demo.DTO.UnitDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Unit;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.TeacherRepository;
import com.example.demo.Repository.UnitRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class UnitService {

    private final UnitRepository unitRepository;
    private final TeacherRepository teacherRepository;
    private final LessonRepository lessonRepository;

    public UnitService(final UnitRepository unitRepository,
            final TeacherRepository teacherRepository, final LessonRepository lessonRepository) {
        this.unitRepository = unitRepository;
        this.teacherRepository = teacherRepository;
        this.lessonRepository = lessonRepository;
    }

    public List<UnitDTO> findAll() {
        final List<Unit> units = unitRepository.findAll(Sort.by("unitId"));
        return units.stream()
                .map(unit -> mapToDTO(unit, new UnitDTO()))
                .toList();
    }

    public UnitDTO get(final Long unitId) {
        return unitRepository.findById(unitId)
                .map(unit -> mapToDTO(unit, new UnitDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final UnitDTO unitDTO) {
        final Unit unit = new Unit();
        mapToEntity(unitDTO, unit);
        return unitRepository.save(unit).getUnitId();
    }

    public void update(final Long unitId, final UnitDTO unitDTO) {
        final Unit unit = unitRepository.findById(unitId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(unitDTO, unit);
        unitRepository.save(unit);
    }

    public void delete(final Long unitId) {
        unitRepository.deleteById(unitId);
    }

    private UnitDTO mapToDTO(final Unit unit, final UnitDTO unitDTO) {
        unitDTO.setUnitId(unit.getUnitId());
        unitDTO.setName(unit.getName());
        unitDTO.setDescription(unit.getDescription());
        unitDTO.setContent(unit.getContent());
        return unitDTO;
    }

    private Unit mapToEntity(final UnitDTO unitDTO, final Unit unit) {
        unit.setName(unitDTO.getName());
        unit.setDescription(unitDTO.getDescription());
        unit.setContent(unitDTO.getContent());
        return unit;
    }

    public ReferencedWarning getReferencedWarning(final Long unitId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Unit unit = unitRepository.findById(unitId)
                .orElseThrow(NotFoundException::new);
        final Teacher unitIdTeacher = teacherRepository.findFirstByUnitId(unit);
        if (unitIdTeacher != null) {
            referencedWarning.setKey("unit.teacher.unitId.referenced");
            referencedWarning.addParam(unitIdTeacher.getTeacherId());
            return referencedWarning;
        }
        final Lesson unitIdLesson = lessonRepository.findFirstByUnitId(unit);
        if (unitIdLesson != null) {
            referencedWarning.setKey("unit.lesson.unitId.referenced");
            referencedWarning.addParam(unitIdLesson.getLessonId());
            return referencedWarning;
        }
        return null;
    }

}
