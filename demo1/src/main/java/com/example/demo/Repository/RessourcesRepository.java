package com.example.demo.Repository;

import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Ressources;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface RessourcesRepository extends MongoRepository<Ressources, Long> {

    Ressources findFirstByLessonId(Lesson lesson);

    List<Ressources> findByUrl(String ressourceUrl);
}
