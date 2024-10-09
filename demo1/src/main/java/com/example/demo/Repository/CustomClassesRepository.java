/*package com.example.demo.Repository;

import com.example.demo.Entity.Classes;
import com.example.demo.Entity.ClassesStats;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomClassesRepository extends MongoRepository<Classes, Long> {

    List<Classes> findClassesByCriteria(String name, String description);

    List<ClassesStats> getClassesStats();

    int countStudentsByClasseId(Long classId);

    // Autres méthodes personnalisées si nécessaire
}
*/