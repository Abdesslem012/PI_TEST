package com.example.demo.Service;



import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.example.demo.DTO.GroupeStudentDTO;
import com.example.demo.Entity.GroupeStudent;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.GroupeStudentRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class GroupeStudentService {

    private final GroupeStudentRepository groupeStudentRepository;
    private final StudentRepository studentRepository;

    public GroupeStudentService(final GroupeStudentRepository groupeStudentRepository,
            final StudentRepository studentRepository) {
        this.groupeStudentRepository = groupeStudentRepository;
        this.studentRepository = studentRepository;
    }

    public List<GroupeStudentDTO> findAll() {
        final List<GroupeStudent> groupeStudents = groupeStudentRepository.findAll(Sort.by("groupstudentId"));
        return groupeStudents.stream()
                .map(groupeStudent -> mapToDTO(groupeStudent, new GroupeStudentDTO()))
                .toList();
    }

    public GroupeStudentDTO get(final Long groupstudentId) {
        return groupeStudentRepository.findById(groupstudentId)
                .map(groupeStudent -> mapToDTO(groupeStudent, new GroupeStudentDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final GroupeStudentDTO groupeStudentDTO) {
        final GroupeStudent groupeStudent = new GroupeStudent();
        mapToEntity(groupeStudentDTO, groupeStudent);
        return groupeStudentRepository.save(groupeStudent).getGroupstudentId();
    }

    public void update(final Long groupstudentId, final GroupeStudentDTO groupeStudentDTO) {
        final GroupeStudent groupeStudent = groupeStudentRepository.findById(groupstudentId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(groupeStudentDTO, groupeStudent);
        groupeStudentRepository.save(groupeStudent);
    }

    public void delete(final Long groupstudentId) {
        groupeStudentRepository.deleteById(groupstudentId);
    }

    private GroupeStudentDTO mapToDTO(final GroupeStudent groupeStudent,
            final GroupeStudentDTO groupeStudentDTO) {
        groupeStudentDTO.setGroupstudentId(groupeStudent.getGroupstudentId());
        groupeStudentDTO.setNomGroupe(groupeStudent.getNomGroupe());
        groupeStudentDTO.setStudentId(groupeStudent.getStudentId());
        return groupeStudentDTO;
    }

    private GroupeStudent mapToEntity(final GroupeStudentDTO groupeStudentDTO,
            final GroupeStudent groupeStudent) {
        groupeStudent.setNomGroupe(groupeStudentDTO.getNomGroupe());
        return groupeStudent;
    }

    public ReferencedWarning getReferencedWarning(final Long groupstudentId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final GroupeStudent groupeStudent = groupeStudentRepository.findById(groupstudentId)
                .orElseThrow(NotFoundException::new);
        final Student groupestudentIdStudent = studentRepository.findFirstByGroupestudentId(groupeStudent);
        if (groupestudentIdStudent != null) {
            referencedWarning.setKey("groupeStudent.student.groupestudentId.referenced");
            referencedWarning.addParam(groupestudentIdStudent.getStudentId());
            return referencedWarning;
        }
        return null;
    }


    public void assignStudentsToGroup(String nomGroupe, Set<Student> studentId) {
        // Recherchez le groupe par son nom
        GroupeStudent groupeStudent = groupeStudentRepository.findByNomGroupe(nomGroupe)
                .orElseThrow(() -> new NotFoundException("Groupe not found: " + nomGroupe));


        // Ajoutez les étudiants au groupe
        groupeStudent.setStudentId(studentId);

        // Enregistrez les modifications dans la base de données
        groupeStudentRepository.save(groupeStudent);
    }



}
