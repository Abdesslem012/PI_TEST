package com.example.demo.Service;
import com.example.demo.DTO.TeacherDTO;
import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Unit;
import com.example.demo.Repository.TeacherRepository;
import com.example.demo.Repository.UnitRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final UnitRepository unitRepository;

    public TeacherService(final TeacherRepository teacherRepository,
                          final UnitRepository unitRepository) {
        this.teacherRepository = teacherRepository;
        this.unitRepository = unitRepository;
    }

    public List<TeacherDTO> findAll() {
        final List<Teacher> teachers = teacherRepository.findAll(Sort.by("teacherId"));
        return teachers.stream()
                .map(teacher -> mapToDTO(teacher, new TeacherDTO()))
                .toList();
    }

    public TeacherDTO get(final Long teacherId) {
        return teacherRepository.findById(teacherId)
                .map(teacher -> mapToDTO(teacher, new TeacherDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final TeacherDTO teacherDTO) {
        final Teacher teacher = new Teacher();
        mapToEntity(teacherDTO, teacher);
        return teacherRepository.save(teacher).getTeacherId();
    }

    public void update(final Long teacherId, final TeacherDTO teacherDTO) {
        final Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(teacherDTO, teacher);
        teacherRepository.save(teacher);
    }

    public void delete(final Long teacherId) {
        teacherRepository.deleteById(teacherId);
    }

    private TeacherDTO mapToDTO(final Teacher teacher, final TeacherDTO teacherDTO) {
        teacherDTO.setTeacherId(teacher.getTeacherId());
        teacherDTO.setFirstName(teacher.getFirstName());
        teacherDTO.setLastName(teacher.getLastName());
        teacherDTO.setEmail(teacher.getEmail());
        teacherDTO.setUnitId(teacher.getUnitId() == null ? null : teacher.getUnitId().getUnitId());
        return teacherDTO;
    }

    private Teacher mapToEntity(final TeacherDTO teacherDTO, final Teacher teacher) {
        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setEmail(teacherDTO.getEmail());
        final Unit unitId = teacherDTO.getUnitId() == null ? null : unitRepository.findById(teacherDTO.getUnitId())
                .orElseThrow(() -> new NotFoundException("unitId not found"));
        teacher.setUnitId(unitId);
        return teacher;
    }

}
