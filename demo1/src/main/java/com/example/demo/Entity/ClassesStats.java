package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "classes_stats")
public class ClassesStats {
    private Long classId;
    private int totalStudents;
    private double averageGrade;
    // Autres statistiques à ajouter
}
