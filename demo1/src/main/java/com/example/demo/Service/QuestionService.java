package com.example.demo.Service;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.Entity.Question;
import com.example.demo.Entity.Quiz;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.util.NotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuizRepository quizRepository;

    private final ResourceLoader resourceLoader;

    public QuestionService(final QuestionRepository questionRepository,
                           final QuizRepository quizRepository, @Qualifier("") ResourceLoader resourceLoader) {
        this.questionRepository = questionRepository;
        this.quizRepository = quizRepository;
        this.resourceLoader = resourceLoader;
    }

    public List<Question> getQuestionsByQuiz(Quiz quizId) {
        return questionRepository.findByQuizId(quizId);
    }

    public List<QuestionDTO> findAll() {
        final List<Question> questions = questionRepository.findAll(Sort.by("questionId"));
        return questions.stream()
                .map(question -> mapToDTO(question, new QuestionDTO()))
                .toList();
    }

    public QuestionDTO get(final Long questionId) {
        return questionRepository.findById(questionId)
                .map(question -> mapToDTO(question, new QuestionDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final QuestionDTO questionDTO) {
        final Question question = new Question();
        mapToEntity(questionDTO, question);
        return questionRepository.save(question).getQuestionId();
    }

    public void update(final Long questionId, final QuestionDTO questionDTO) {
        final Question question = questionRepository.findById(questionId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(questionDTO, question);
        questionRepository.save(question);
    }

    public void delete(final Long questionId) {
        questionRepository.deleteById(questionId);
    }

    private QuestionDTO mapToDTO(final Question question, final QuestionDTO questionDTO) {
        questionDTO.setQuestionId(question.getQuestionId());
        questionDTO.setText(question.getText());
        //questionDTO.setCorrectAnswer(question.getCorrectAnswer());
        questionDTO.setPossibleAnswers(question.getPossibleAnswers());
        questionDTO.setCorrectAnswerIndex(question.getCorrectAnswerIndex());
        questionDTO.setQuizId(question.getQuizId().getQuizId());
        //questionDTO.setQuizId(question.getQuizId() == null ? null : question.getQuizId().getQuizId());
        return questionDTO;
    }

    private Question mapToEntity(final QuestionDTO questionDTO, final Question question) {
        question.setText(questionDTO.getText());
        question.setPossibleAnswers(questionDTO.getPossibleAnswers());
        question.setCorrectAnswerIndex(questionDTO.getCorrectAnswerIndex());
        //question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        final Quiz quizId = questionDTO.getQuizId() == null ? null : quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new NotFoundException("quizId not found"));
        question.setQuizId(quizId);
        return question;
    }

    public void insertQuestionsFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:questions.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {});

        List<Question> questions = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("questions".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    QuestionDTO questionDTO = objectMapper.convertValue(obj, QuestionDTO.class);
                    Question question = convertToQuestion(questionDTO);
                    questions.add(question);
                }
            }
        }
        // Enregistrez la liste des objets Sector dans la base de données
        questionRepository.saveAll(questions);
    }

    private Question convertToQuestion(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setQuestionId(questionDTO.getQuestionId());
        question.setText(questionDTO.getText());
        question.setPossibleAnswers(questionDTO.getPossibleAnswers());
        question.setCorrectAnswerIndex(questionDTO.getCorrectAnswerIndex());
        //question.setCorrectAnswer(questionDTO.getCorrectAnswer());

        /*Quiz quiz = quizRepository.findById(questionDTO.getQuizId())
                .orElseThrow(() -> new NotFoundException("Quiz not found with ID " + questionDTO.getQuizId()));
        question.setQuizId(quiz); // Associer le Quiz à la Question*/

        return question;
    }



}
