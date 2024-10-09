package com.example.demo.Service;
import com.example.demo.DTO.PresenceDTO;
import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Presence;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.LessonRepository;
import com.example.demo.Repository.PresenceRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.NotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PresenceService {

    private final PresenceRepository presenceRepository;
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;

    public PresenceService(final PresenceRepository presenceRepository,
                           final LessonRepository lessonRepository,StudentRepository studentRepository) {
        this.presenceRepository = presenceRepository;
        this.lessonRepository = lessonRepository;
        this.studentRepository= studentRepository;
    }

    public List<PresenceDTO> findAll() {
        final List<Presence> presences = presenceRepository.findAll(Sort.by("presenceId"));
        return presences.stream()
                .map(presence -> mapToDTO(presence, new PresenceDTO()))
                .toList();
    }

    public PresenceDTO get(final Long presenceId) {
        return presenceRepository.findById(presenceId)
                .map(presence -> mapToDTO(presence, new PresenceDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final PresenceDTO presenceDTO) {
        final Presence presence = new Presence();
        mapToEntity(presenceDTO, presence);
        return presenceRepository.save(presence).getPresenceId();
    }


    public void update(final Long presenceId, final PresenceDTO presenceDTO) {
        final Presence presence = presenceRepository.findById(presenceId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(presenceDTO, presence);
        presenceRepository.save(presence);
    }

    public void delete(final Long presenceId) {
        presenceRepository.deleteById(presenceId);
    }

    // Méthode pour mapper l'étudiant et son nom dans le DTO
    private PresenceDTO mapToDTO(final Presence presence, final PresenceDTO presenceDTO) {
        presenceDTO.setPresenceId(presence.getPresenceId());
        presenceDTO.setSuiviCours(presence.getSuiviCours());
        presenceDTO.setPresence(presence.getPresence());
        presenceDTO.setStudentId(presence.getStudent().getStudentId());
        presenceDTO.setStudentName(presence.getStudent().getFirstName() + " " + presence.getStudent().getLastName());
        return presenceDTO;
    }


    private Presence mapToEntity(final PresenceDTO presenceDTO, final Presence presence) {
        presence.setSuiviCours(presenceDTO.getSuiviCours());
        presence.setPresence(presenceDTO.getPresence());

        // Assurez-vous que l'identifiant de l'étudiant est défini dans le DTO
        if (presenceDTO.getStudentId() != null) {
            // Récupérez l'objet Student à partir de son ID
            Student student = studentRepository.findById(presenceDTO.getStudentId())
                    .orElseThrow(() -> new NotFoundException("Student not found"));
            presence.setStudent(student);
           // presence.setStudentNames(student.getFirstName() + " " + student.getLastName());
        } else {
            // Gérez le cas où l'ID de l'étudiant n'est pas défini dans le DTO
            // Peut-être lever une exception ou effectuer une autre action appropriée
        }

        // Affectez le cours en fonction de l'ID du cours dans le DTO
       /* final Lesson lessonId = presenceDTO.getCoursId() == null ? null : lessonRepository.findById(presenceDTO.getCoursId())
                .orElseThrow(() -> new NotFoundException("coursId not found"));
        presence.setLessonId(lessonId);*/

        return presence;
    }



}