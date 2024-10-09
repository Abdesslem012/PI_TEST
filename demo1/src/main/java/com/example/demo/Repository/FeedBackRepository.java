package com.example.demo.Repository;

import com.example.demo.Entity.FeedBack;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeedBackRepository extends MongoRepository<FeedBack, Long> {

    //FeedBack findFirstByCLessonId(Lesson lesson);

}
