package com.example.demo.Repository;


import com.example.demo.Entity.MoitoringAcadimicObjectives;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MoitoringAcadimicObjectivesRepository extends MongoRepository<MoitoringAcadimicObjectives, Long> {

    boolean existsByMaoIdIgnoreCase(@NotNull Long maoId);


    default double calculateAverageGrades() {
        // Implémentez votre logique pour calculer la moyenne des notes
        // par exemple, en accédant aux données depuis la base de données
        return 85.5; // Valeur de test, remplacez-la par votre logique réelle
    }

    default double calculateSuccessRate() {
        // Implémentez votre logique pour calculer le taux de réussite
        // par exemple, en accédant aux données depuis la base de données
        return 0.75; // Valeur de test, remplacez-la par votre logique réelle
    }

    default int calculateAttendancePercentage() {
        // Implémentez votre logique pour calculer le pourcentage de présence
        // par exemple, en accédant aux données depuis la base de données
        return 90; // Valeur de test, remplacez-la par votre logique réelle
    }
}
