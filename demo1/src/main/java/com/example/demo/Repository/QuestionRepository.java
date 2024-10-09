package com.example.demo.Repository;

import com.example.demo.Entity.Question;
import com.example.demo.Entity.Quiz;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface QuestionRepository extends MongoRepository<Question, Long> {

    Question findFirstByQuizId(Quiz quiz);

    List<Question> findByText(String questionText);

    List<Question> findByQuizId(Quiz quiz);
}
