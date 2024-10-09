package com.example.demo.Repository;

import com.example.demo.Entity.Quiz;
import com.example.demo.Entity.Rating;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface RatingRepository extends MongoRepository<Rating, Long> {

    Rating findFirstByQuizId(Quiz quiz);

    boolean existsByQuizIdQuizId(Long quizId);

    Optional<Rating> findByNom(String ratingNom);
}
