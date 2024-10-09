package com.example.demo.Repository;

import com.example.demo.Entity.Lesson;
import com.example.demo.Entity.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface LessonRepository extends MongoRepository<Lesson, Long> {

    Lesson findFirstByUnitId(Unit unit);

    //Lesson findByDescription(String description);

    Optional<Lesson> findByDescription(String lessonDescription);
}
