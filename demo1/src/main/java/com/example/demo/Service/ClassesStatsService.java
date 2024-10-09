/*package com.example.demo.Service;


import com.example.demo.Entity.ClassesStats;
import com.example.demo.Repository.CustomClassesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassesStatsService {

    @Autowired
    private CustomClassesRepository customClassesRepository;

    public ClassesStats getClassStats(Long classId) {
        ClassesStats stats = new ClassesStats();
        // Récupérer le nombre total d'étudiants inscrits dans la classe
        stats.setTotalStudents(customClassesRepository.countStudentsByClassId(classId));
        // Calculer la moyenne des notes des étudiants, etc.
        // Ajoutez d'autres opérations pour calculer les statistiques nécessaires
        return stats;
    }
}*/