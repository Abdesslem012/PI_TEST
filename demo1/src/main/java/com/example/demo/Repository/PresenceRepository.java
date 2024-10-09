package com.example.demo.Repository;

import com.example.demo.Entity.Presence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PresenceRepository extends MongoRepository<Presence, Long> {

    //Presence findFirstByLessonId(Lesson lesson);

}
