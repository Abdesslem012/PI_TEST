package com.example.demo.Service;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.example.demo.DTO.MoitoringAcadimicObjectivesDTO;
import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Entity.Objectives;
import com.example.demo.Entity.PersonalizedAdvice;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.MoitoringAcadimicObjectivesRepository;
import com.example.demo.Repository.ObjectivesRepository;
import com.example.demo.Repository.PersonalizedAdviceRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;




@Service
public class MoitoringAcadimicObjectivesService {

    private final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository;
    private final StudentRepository studentRepository;
    private final ObjectivesRepository objectivesRepository;
    private final PersonalizedAdviceRepository personalizedAdviceRepository;



    public MoitoringAcadimicObjectivesService(
            final MoitoringAcadimicObjectivesRepository moitoringAcadimicObjectivesRepository,
            final StudentRepository studentRepository,
            final ObjectivesRepository objectivesRepository,
            final PersonalizedAdviceRepository personalizedAdviceRepository) {
        this.moitoringAcadimicObjectivesRepository = moitoringAcadimicObjectivesRepository;
        this.studentRepository = studentRepository;
        this.objectivesRepository = objectivesRepository;
        this.personalizedAdviceRepository = personalizedAdviceRepository;
    }

    public List<MoitoringAcadimicObjectivesDTO> findAll() {
        final List<MoitoringAcadimicObjectives> moitoringAcadimicObjectiveses = moitoringAcadimicObjectivesRepository.findAll(Sort.by("maoId"));
        return moitoringAcadimicObjectiveses.stream()
                .map(moitoringAcadimicObjectives -> mapToDTO(moitoringAcadimicObjectives, new MoitoringAcadimicObjectivesDTO()))
                .toList();
    }

    public MoitoringAcadimicObjectivesDTO get(final Long maoId) {
        return moitoringAcadimicObjectivesRepository.findById(maoId)
                .map(moitoringAcadimicObjectives -> mapToDTO(moitoringAcadimicObjectives, new MoitoringAcadimicObjectivesDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public @NotNull Long create(final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        final MoitoringAcadimicObjectives moitoringAcadimicObjectives = new MoitoringAcadimicObjectives();
        mapToEntity(moitoringAcadimicObjectivesDTO, moitoringAcadimicObjectives);
        moitoringAcadimicObjectives.setMaoId(moitoringAcadimicObjectivesDTO.getMaoId()); // Utiliser directement le champ maoId de type Long
        return moitoringAcadimicObjectivesRepository.save(moitoringAcadimicObjectives).getMaoId();
    }



    public void update(final Long maoId,
            final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        final MoitoringAcadimicObjectives moitoringAcadimicObjectives = moitoringAcadimicObjectivesRepository.findById(maoId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(moitoringAcadimicObjectivesDTO, moitoringAcadimicObjectives);
        moitoringAcadimicObjectivesRepository.save(moitoringAcadimicObjectives);
    }

    public void delete(final Long maoId) {
        moitoringAcadimicObjectivesRepository.deleteById(maoId);
    }

    private MoitoringAcadimicObjectivesDTO mapToDTO(
            final MoitoringAcadimicObjectives moitoringAcadimicObjectives,
            final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO) {
        moitoringAcadimicObjectivesDTO.setMaoId(moitoringAcadimicObjectives.getMaoId()); // Utiliser directement le champ maoId de type Long
        moitoringAcadimicObjectivesDTO.setSubject(moitoringAcadimicObjectives.getSubject());
        moitoringAcadimicObjectivesDTO.setAverage(moitoringAcadimicObjectives.getAverage());
        moitoringAcadimicObjectivesDTO.setDescription(moitoringAcadimicObjectives.getDescription());
        moitoringAcadimicObjectivesDTO.setDeadline(moitoringAcadimicObjectives.getDeadline());

        return moitoringAcadimicObjectivesDTO;
    }



    private MoitoringAcadimicObjectives mapToEntity(
            final MoitoringAcadimicObjectivesDTO moitoringAcadimicObjectivesDTO,
            final MoitoringAcadimicObjectives moitoringAcadimicObjectives) {
        moitoringAcadimicObjectives.setMaoId(moitoringAcadimicObjectivesDTO.getMaoId());
        moitoringAcadimicObjectives.setSubject(moitoringAcadimicObjectivesDTO.getSubject());
        moitoringAcadimicObjectives.setAverage(moitoringAcadimicObjectivesDTO.getAverage());
        moitoringAcadimicObjectives.setDescription(moitoringAcadimicObjectivesDTO.getDescription());
        moitoringAcadimicObjectives.setDeadline(moitoringAcadimicObjectivesDTO.getDeadline());

        return moitoringAcadimicObjectives;
    }

    public boolean maoIdExists(final Long maoId) {
        return moitoringAcadimicObjectivesRepository.existsByMaoIdIgnoreCase(maoId);
    }

    public ReferencedWarning getReferencedWarning(final Long maoId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MoitoringAcadimicObjectives moitoringAcadimicObjectives = moitoringAcadimicObjectivesRepository.findById(maoId)
                .orElseThrow(NotFoundException::new);
        final Student monitoringldStudent = studentRepository.findFirstByMonitoringld(moitoringAcadimicObjectives);
        if (monitoringldStudent != null) {
            referencedWarning.setKey("moitoringAcadimicObjectives.student.monitoringld.referenced");
            referencedWarning.addParam(monitoringldStudent.getStudentId());
            return referencedWarning;
        }
        final Objectives objectivesObjectives = objectivesRepository.findFirstByObjectives(moitoringAcadimicObjectives);
        if (objectivesObjectives != null) {
            referencedWarning.setKey("moitoringAcadimicObjectives.objectives.objectives.referenced");
            referencedWarning.addParam(objectivesObjectives.getObjectivesId());
            return referencedWarning;
        }
        final PersonalizedAdvice moitoringIdPersonalizedAdvice = personalizedAdviceRepository.findFirstByMoitoringId(moitoringAcadimicObjectives);
        if (moitoringIdPersonalizedAdvice != null) {
            referencedWarning.setKey("moitoringAcadimicObjectives.personalizedAdvice.moitoringId.referenced");
            referencedWarning.addParam(moitoringIdPersonalizedAdvice.getIdPersonalized());
            return referencedWarning;
        }
        return null;
    }

    public List<PersonalizedAdvice> generatePersonalizedAdvices() {
        List<MoitoringAcadimicObjectives> objectivesList = moitoringAcadimicObjectivesRepository.findAll();
        List<PersonalizedAdvice> advices = new ArrayList<>();

        for (MoitoringAcadimicObjectives objectives : objectivesList) {
            PersonalizedAdvice advice = new PersonalizedAdvice();
            advice.setMoitoringId(objectives);

            // Génération d'un conseil personnalisé aléatoire
            String[] possibleAdvices = {
                    "Concentrez-vous sur votre objectif principal et divisez-le en tâches plus petites.",
                    "Utilisez des techniques de gestion du temps pour maximiser votre efficacité.",
                    "Prenez des pauses régulières pour éviter le surmenage et la fatigue.",
                    "Demandez de l'aide et des conseils à vos enseignants et pairs si nécessaire."
            };
            Random random = new Random();
            int index = random.nextInt(possibleAdvices.length);
            advice.setAdviceText(possibleAdvices[index]);

            advices.add(advice);
        }

        return personalizedAdviceRepository.saveAll(advices);
    }



    public MoitoringAcadimicObjectivesDTO calculateAcademicPerformance() {
        double averageGrades = moitoringAcadimicObjectivesRepository.calculateAverageGrades();
        double successRate = moitoringAcadimicObjectivesRepository.calculateSuccessRate();
        int attendancePercentage = moitoringAcadimicObjectivesRepository.calculateAttendancePercentage();

        MoitoringAcadimicObjectivesDTO dto = new MoitoringAcadimicObjectivesDTO();
        dto.setAverage(averageGrades);
        dto.setSuccessRate(successRate);
        dto.setAttendancePercentage(attendancePercentage);

        return dto;
    }



}
