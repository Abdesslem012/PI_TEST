package com.example.demo.Service;

import com.example.demo.DTO.StudentDTO;
import com.example.demo.Entity.*;
import com.example.demo.Repository.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


import com.example.demo.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;


@Service
public class StudentService {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ApplicationContext applicationContext;

    private final StudentRepository studentRepository;
    private final ClassesRepository classesRepository;
    private final GroupeStudentRepository groupeStudentRepository;
    private final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository;
    private final SectorRepository sectorRepository;
    private final ApplicationEventPublisher eventPublisher;

    private final BadgeRepository badgeRepository;


    public StudentService(final StudentRepository studentRepository,
                          final ClassesRepository classesRepository,
                          final GroupeStudentRepository groupeStudentRepository,
                          final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository,
                          final SectorRepository sectorRepository, ApplicationEventPublisher eventPublisher, BadgeRepository badgeRepository) {
        this.studentRepository = studentRepository;
        this.classesRepository = classesRepository;
        this.groupeStudentRepository = groupeStudentRepository;
        this.moitoringAcadimicObjectivesRepository = moitoringAcadimicObjectivesRepository;
        this.sectorRepository = sectorRepository;
        this.eventPublisher = eventPublisher;
        this.badgeRepository = badgeRepository;
    }

    public List<StudentDTO> findAll() {
        final List<Student> students = studentRepository.findAll(Sort.by("studentId"));
        return students.stream()
                .map(student -> mapToDTO(student, new StudentDTO()))
                .toList();
    }

    public StudentDTO get(final Long studentId) {
        return studentRepository.findById(studentId)
                .map(student -> mapToDTO(student, new StudentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StudentDTO studentDTO) {
        final Student student = new Student();
        mapToEntity(studentDTO, student);
        //applicationContext.publishEvent(new StudentAddedEvent(this, student));
        return studentRepository.save(student).getStudentId();
    }

    public void update(final Long studentId, final StudentDTO studentDTO) {
        final Student student = studentRepository.findById(studentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(studentDTO, student);
        studentRepository.save(student);
    }

    public void delete(final Long studentId) {
        studentRepository.deleteById(studentId);
    }

    private StudentDTO mapToDTO(final Student student, final StudentDTO studentDTO) {
        studentDTO.setStudentId(student.getStudentId());
        studentDTO.setFirstName(student.getFirstName());
        studentDTO.setLastName(student.getLastName());
        studentDTO.setEmail(student.getEmail());
        studentDTO.setDateOfBirth(student.getDateOfBirth());
        studentDTO.setClasses(student.getClasses() == null ? null : student.getClasses().getClasseId());
        studentDTO.setGroupestudentId(student.getGroupestudentId() == null ? null : student.getGroupestudentId().getGroupstudentId());
        studentDTO.setMonitoringld(student.getMonitoringld() == null ? null : student.getMonitoringld().getMaoId());
        studentDTO.setSectorId(student.getSectorId() == null ? null : student.getSectorId().getSectorId());
        if (student.getEarnedBadges() != null) {
            studentDTO.setEarnedBadges(student.getEarnedBadges().stream()
                    .map(Badge::getBadgeId)
                    .collect(Collectors.toSet()));
        }
        return studentDTO;
    }

    public Student mapToEntity(StudentDTO studentDTO, Student student) {
        student.setFirstName(studentDTO.getFirstName());
        student.setLastName(studentDTO.getLastName());
        student.setEmail(studentDTO.getEmail());
        student.setDateOfBirth(studentDTO.getDateOfBirth());
        final Classes classes = studentDTO.getClasses() == null ? null : classesRepository.findById(studentDTO.getClasses())
                .orElseThrow(() -> new NotFoundException("classes not found"));
        student.setClasses(classes);
        final GroupeStudent groupestudentId = studentDTO.getGroupestudentId() == null ? null : groupeStudentRepository.findById(studentDTO.getGroupestudentId())
                .orElseThrow(() -> new NotFoundException("groupestudentId not found"));
        student.setGroupestudentId(groupestudentId);
        final MoitoringAcadimicObjectives monitoringld = studentDTO.getMonitoringld() == null ? null : moitoringAcadimicObjectivesRepository.findById(Long.valueOf(String.valueOf(studentDTO.getMonitoringld())))
                .orElseThrow(() -> new NotFoundException("monitoringld not found"));
        student.setMonitoringld(monitoringld);
        final Sector sectorId = studentDTO.getSectorId() == null ? null : sectorRepository.findById(studentDTO.getSectorId())
                .orElseThrow(() -> new NotFoundException("sectorId not found"));
        student.setSectorId(sectorId);
        if (studentDTO.getEarnedBadges() != null && !studentDTO.getEarnedBadges().isEmpty()) {
            // Convertit la liste des ID de questions en un ensemble de questions
            Set<Badge> badges = studentDTO.getEarnedBadges().stream()
                    .map(badgeId -> badgeRepository.findById(badgeId)
                            .orElseThrow(() -> new NotFoundException("Badge not found with id: " + badgeId)))
                    .collect(Collectors.toSet());
            student.setEarnedBadges(badges);
        }
        return student;
    }

    public boolean monitoringldExists(final Long maoId) {
        return studentRepository.existsByMonitoringldMaoId(maoId);
    }

    public Student findByFirstNameAndLastName(String firstName, String lastName) {
        Optional<Student> optionalStudent = studentRepository.findByFirstNameAndLastName(firstName, lastName);
        return optionalStudent.orElse(null);
    }


    public boolean isEmailUnique(String email) {
        return studentRepository.findByEmail(email) == null;
    }


    public List<Student> findByFilters(String firstName, String lastName, Long classId) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Student> query = builder.createQuery(Student.class);
        Root<Student> root = query.from(Student.class);

        List<Predicate> predicates = new ArrayList<>();

        if (firstName != null && !firstName.isEmpty()) {
            predicates.add(builder.equal(root.get("firstName"), firstName));
        }
        if (lastName != null && !lastName.isEmpty()) {
            predicates.add(builder.equal(root.get("lastName"), lastName));
        }
        if (classId != null) {
            Join<Student, Classes> classesJoin = root.join("classes");
            predicates.add(builder.equal(classesJoin.get("classId"), classId));
        }

        query.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Student> typedQuery = entityManager.createQuery(query);
        return typedQuery.getResultList();
    }


    public boolean existsById(final Long studentId) {
        return studentRepository.existsById(studentId);
    }

    public Student assignStudentToClass(String studentFirstName, String studentLastName, String className) {
        // Recherche de l'étudiant par son prénom et son nom
        Student student = studentRepository.findByFirstNameAndLastName(studentFirstName, studentLastName)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with name: " + studentFirstName + " " + studentLastName));

        // Recherche de la classe par son nom
        Classes classe = classesRepository.findByName(className)
                .orElseThrow(() -> new EntityNotFoundException("Class not found with name: " + className));

        // Attribution de l'étudiant à la classe
        student.setClasses(classe);
        return studentRepository.save(student);
    }

}
