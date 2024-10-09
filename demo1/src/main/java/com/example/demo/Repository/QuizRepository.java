package com.example.demo.Repository;

import com.example.demo.Entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface QuizRepository extends MongoRepository<Quiz, Long> {
    Optional<Quiz> findByTitle(String quizTitle);
}
