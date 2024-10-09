package com.example.demo.Service;


import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.demo.DTO.ClassesDTO;
import com.example.demo.DTO.StudentDTO;
import com.example.demo.Entity.Classes;
import com.example.demo.Entity.ClassesStats;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.ClassesRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Repository.TeacherRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
public class ClassesService {

    private final ClassesRepository classesRepository;
    private final StudentRepository studentRepository;

   //private final CustomClassesRepository customClassesRepository;

    public ClassesService(final ClassesRepository classesRepository,
                          final StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.classesRepository = classesRepository;
        this.studentRepository = studentRepository;
        //this.customClassesRepository = customClassesRepository;
    }



    public List<ClassesDTO> findAll() {
        final List<Classes> classeses = classesRepository.findAll(Sort.by("classeId"));
        return classeses.stream()
                .map(classes -> mapToDTO(classes, new ClassesDTO()))
                .toList();
    }

    public ClassesDTO get(final String classeId) {
        return classesRepository.findById(classeId)
                .map(classes -> mapToDTO(classes, new ClassesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public String create(final ClassesDTO classesDTO) {
        final Classes classes = new Classes();
        mapToEntity(classesDTO, classes);
        return classesRepository.save(classes).getClasseId();
    }

    public void update(final String classeId, final ClassesDTO classesDTO) {
        final Classes classes = classesRepository.findById(classeId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(classesDTO, classes);
        classesRepository.save(classes);
    }

    public void delete(final String classeId) {
        classesRepository.deleteById(classeId);
    }

    public void addStudentToClass(String className, String studentFirstName, String studentLastName) {
        Classes classes = classesRepository.findByName(className)
                .orElseThrow(NotFoundException::new);
        Student student = studentRepository.findByFirstNameAndLastName(studentFirstName, studentLastName)
                .orElseThrow(NotFoundException::new);
        classes.getStudents().add(student); // Add the entire Student object
        classesRepository.save(classes);
    }

    public void assignStudentToClass(String className, String studentFirstName, String studentLastName) {
        // Trouver la classe par son nom
        Classes classes = classesRepository.findByName(className)
                .orElseThrow(() -> new NotFoundException("Classe non trouvée"));

        // Trouver l'étudiant par son prénom et son nom
        Student student = studentRepository.findByFirstNameAndLastName(studentFirstName, studentLastName)
                .orElseThrow(() -> new NotFoundException("Étudiant non trouvé"));

        // Récupérer la liste d'étudiants de la classe et y ajouter l'étudiant trouvé
        Set<Student> students = classes.getStudentId();
        students.add(student);

        // Mettre à jour la liste d'étudiants de la classe et sauvegarder
        classes.setStudentId(students);
        classesRepository.save(classes);
    }




    public void assignAllStudentsToClass(String className) {
        Classes classes = classesRepository.findByName(className)
                .orElseThrow(NotFoundException::new);

        List<Student> allStudents = studentRepository.findAll();

        for (Student student : allStudents) {
            classes.getStudents().add(student); // Add the entire Student object
        }

        classesRepository.save(classes);
    }

    private StudentDTO mapStudentToDTO(Student student) {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(student.getStudentId());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        // Map other student properties if needed
        return dto;
    }




    private ClassesDTO mapToDTO(final Classes classes, final ClassesDTO classesDTO) {
        classesDTO.setClasseId(classes.getClasseId());
        classesDTO.setName(classes.getName());
        classesDTO.setDescription(classes.getDescription());
        return classesDTO;
    }

    private Classes mapToEntity(final ClassesDTO classesDTO, final Classes classes) {
        classes.setName(classesDTO.getName());
        classes.setDescription(classesDTO.getDescription());
        return classes;
    }

    public ReferencedWarning getReferencedWarning(final String classeId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Classes classes = classesRepository.findById(classeId)
                .orElseThrow(NotFoundException::new);
        final Student classesStudent = studentRepository.findFirstByClasses(classes);
        if (classesStudent != null) {
            referencedWarning.setKey("classes.student.classes.referenced");
            referencedWarning.addParam(classesStudent.getStudentId());
            return referencedWarning;
        }
        return null;
    }


    public Classes findByName(String className) {
        Optional<Classes> optionalClass = classesRepository.findByName(className);
        return optionalClass.orElse(null);
    }

    public void save(Classes classes) {
        classesRepository.save(classes);
    }

    public List<StudentDTO> getAllStudentsWithClasses() {
        List<Student> students = studentRepository.findAll();
        return students.stream()
                .map(this::mapStudentToDTO)
                .collect(Collectors.toList());
    }


    public List<StudentDTO> getAssignedStudentsForClass(String className) {
        Classes classes = classesRepository.findByName(className)
                .orElseThrow(() -> new NotFoundException("Classe non trouvée"));

        Set<Student> assignedStudents = classes.getStudentId();
        List<StudentDTO> assignedStudentDTOs = assignedStudents.stream()
                .map(this::mapStudentToDTO)
                .collect(Collectors.toList());

        return assignedStudentDTOs;
    }


   /* public List<Classes> findClassesByCriteria(String name, String description) {
        ClassesService customClassesRepository;
        return customClassesRepository.findClassesByCriteria(name, description);
    }
/*
    public List<ClassesStats> getClassesStats() {
        return customClassesRepository.getClassesStats();
    }
*/





}
