package com.example.demo.Service;

import com.example.demo.DTO.BadgeDTO;
import com.example.demo.Entity.Badge;
import com.example.demo.Entity.Quiz;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.BadgeRepository;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BadgeService {

    private final BadgeRepository badgeRepository;
    private final QuizRepository quizRepository;
    private final StudentRepository studentRepository;

    private final ResourceLoader resourceLoader;

    public BadgeService(BadgeRepository badgeRepository, QuizRepository quizRepository, StudentRepository studentRepository, @Qualifier("") ResourceLoader resourceLoader) {
        this.badgeRepository = badgeRepository;
        this.quizRepository = quizRepository;
        this.studentRepository = studentRepository;
        this.resourceLoader = resourceLoader;
    }

    // Méthode pour récupérer tous les badges
    public List<Badge> getAllBadges() {
        return badgeRepository.findAll();
    }

    public BadgeDTO get(final Long badgeId) {
        return badgeRepository.findById(badgeId)
                .map(badges -> mapToDTO(badges, new BadgeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    private BadgeDTO mapToDTO(final Badge badge, final BadgeDTO badgeDTO) {
        badgeDTO.setBadgeId(badge.getBadgeId());
        badgeDTO.setName(badge.getName());
        badgeDTO.setDescription(badge.getDescription());
        return badgeDTO;
    }

    private Badge mapToEntity(final BadgeDTO badgeDTO, final Badge badge) {
        badge.setName(badgeDTO.getName());
        badge.setDescription(badgeDTO.getDescription());
        return badge;
    }

    // Méthode pour enregistrer un nouveau badge
    public Badge saveBadge(Badge badge) {
        return badgeRepository.save(badge);
    }

    // Méthode pour supprimer un badge par son ID
    public void deleteBadgeById(Long badgeId) {
        badgeRepository.deleteById(badgeId);
    }

    // Méthode pour associer des badges à un quiz
    public void assignBadgesToQuiz(Long quizId, Set<Badge> badges) {
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            // Assurez-vous que la liste des badges n'est pas vide
            if (!badges.isEmpty()) {
                // Associez les badges au quiz
                quiz.setBadgeList(badges);
                // Enregistrez le quiz mis à jour
                quizRepository.save(quiz);
            } else {
                System.out.println("La liste des badges est vide.");
            }
        } else {
            System.out.println("Le quiz avec l'ID " + quizId + " n'existe pas.");
        }
    }

    // Méthode pour attribuer des badges à un étudiant
    public void assignBadgesToStudent(Long studentId, List<Badge> badges) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student != null) {
            // Assurez-vous que la liste des badges n'est pas vide
            if (!badges.isEmpty()) {
                // Ajoutez les badges à la liste des badges gagnés par l'étudiant
                student.getEarnedBadges().addAll(badges);
                // Enregistrez l'étudiant mis à jour
                studentRepository.save(student);
            } else {
                System.out.println("La liste des badges est vide.");
            }
        } else {
            System.out.println("L'étudiant avec l'ID " + studentId + " n'existe pas.");
        }
    }


    public void insertBadgeFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:badge.json").getInputStream();

        // Lire directement la liste des QuizDTO depuis le fichier JSON
        List<BadgeDTO> badgeDTOList = objectMapper.readValue(inputStream, new TypeReference<List<BadgeDTO>>() {});

        // Convertir chaque QuizDTO en Quiz et les ajouter à une liste
        List<Badge> Badges = badgeDTOList.stream()
                .map(this::convertToBadge)
                .collect(Collectors.toList());

        // Enregistrer la liste des Quiz dans la base de données
        badgeRepository.saveAll(Badges);
    }

    private Badge convertToBadge(BadgeDTO badgeDTO) {
        Badge badge = new Badge();
        badge.setBadgeId(badgeDTO.getBadgeId());
        badge.setName(badgeDTO.getName());
        badge.setDescription(badgeDTO.getDescription());
        return badge;
    }



}