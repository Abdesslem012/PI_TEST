package com.example.demo.Service;

import com.example.demo.DTO.QuizDTO;
import com.example.demo.Entity.Badge;
import com.example.demo.Entity.Question;
import com.example.demo.Entity.Quiz;
import com.example.demo.Entity.Rating;
import com.example.demo.Repository.BadgeRepository;
import com.example.demo.Repository.QuestionRepository;
import com.example.demo.Repository.QuizRepository;
import com.example.demo.Repository.RatingRepository;
import com.example.demo.util.NotFoundException;
import com.example.demo.util.ReferencedWarning;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final RatingRepository ratingRepository;
    private final QuestionRepository questionRepository;

    private final ResourceLoader resourceLoader;

    private final BadgeRepository badgeRepository;
    public QuizService(final QuizRepository quizRepository, final RatingRepository ratingRepository,
                       final QuestionRepository questionRepository, @Qualifier("") ResourceLoader resourceLoader, BadgeRepository badgeRepository) {
        this.quizRepository = quizRepository;
        this.ratingRepository = ratingRepository;
        this.questionRepository = questionRepository;
        this.resourceLoader = resourceLoader;
        this.badgeRepository = badgeRepository;
    }


    public void assignQuestionsToQuizs(String quizTitle, List<String> questionTexts) {
        Quiz quiz = quizRepository.findByTitle(quizTitle)
                .orElseThrow(() -> new RuntimeException("Quiz not found with title: " + quizTitle));

        questionTexts.forEach(questionText -> {
            List<Question> questions = questionRepository.findByText(questionText);
            questions.forEach(question -> {
                question.setQuizId(quiz); // Assurez-vous d'avoir une méthode setQuiz dans Question
                questionRepository.save(question);
            });
        });
    }

    public void unassignQuestionsFromQuiz(Long quizId, List<Long> questions) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        questions.forEach(questionId -> {
            Question question = questionRepository.findById(questionId)
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + questionId));
            question.setQuizId(null); // Désaffecter la question du quiz
            questionRepository.save(question);
        });
    }

    public List<QuizDTO> findAll() {
        final List<Quiz> quizzes = quizRepository.findAll(Sort.by("quizId"));
        return quizzes.stream()
                .map(quiz -> mapToDTO(quiz, new QuizDTO()))
                .toList();
    }

    public QuizDTO get(final Long quizId) {
        return quizRepository.findById(quizId)
                .map(quiz -> mapToDTO(quiz, new QuizDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final QuizDTO quizDTO) {
        final Quiz quiz = new Quiz();
        mapToEntity(quizDTO, quiz);
        return quizRepository.save(quiz).getQuizId();
    }

    public void update(final Long quizId, final QuizDTO quizDTO) {
        final Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(quizDTO, quiz);
        quizRepository.save(quiz);
    }

    public void delete(final Long quizId) {
        quizRepository.deleteById(quizId);
    }

    private QuizDTO mapToDTO(final Quiz quiz, final QuizDTO quizDTO) {
        quizDTO.setQuizId(quiz.getQuizId());
        quizDTO.setTitle(quiz.getTitle());
        quizDTO.setDescription(quiz.getDescription());
        quizDTO.setDuration(quiz.getDuration());
        if (quiz.getQuestions() != null) {
            quizDTO.setQuestions(quiz.getQuestions().stream()
                    .map(Question::getQuestionId)
                    .collect(Collectors.toSet()));
        }
        if (quiz.getBadgeList() != null) {
            quizDTO.setBadgeList(quiz.getBadgeList().stream()
                    .map(Badge::getBadgeId)
                    .collect(Collectors.toSet()));
        }
        return quizDTO;
    }

    private Quiz mapToEntity(final QuizDTO quizDTO, final Quiz quiz) {
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDuration(quizDTO.getDuration());
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            // Convertit la liste des ID de questions en un ensemble de questions
            Set<Question> questions = quizDTO.getQuestions().stream()
                    .map(questionId -> questionRepository.findById(questionId)
                            .orElseThrow(() -> new NotFoundException("Question not found with id: " + questionId)))
                    .collect(Collectors.toSet());
            quiz.setQuestions(questions);
        }
        if (quizDTO.getBadgeList() != null && !quizDTO.getBadgeList().isEmpty()) {
            // Convertit la liste des ID de questions en un ensemble de questions
            Set<Badge> badges = quizDTO.getBadgeList().stream()
                    .map(badgeId -> badgeRepository.findById(badgeId)
                            .orElseThrow(() -> new NotFoundException("Badge not found with id: " + badgeId)))
                    .collect(Collectors.toSet());
            quiz.setBadgeList(badges);
        }
        return quiz;
    }

    public ReferencedWarning getReferencedWarning(final Long quizId) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(NotFoundException::new);
        final Rating quizIdRating = ratingRepository.findFirstByQuizId(quiz);
        if (quizIdRating != null) {
            referencedWarning.setKey("quiz.rating.quizId.referenced");
            referencedWarning.addParam(quizIdRating.getRatingId());
            return referencedWarning;
        }
        final Question quizIdQuestion = questionRepository.findFirstByQuizId(quiz);
        if (quizIdQuestion != null) {
            referencedWarning.setKey("quiz.question.quizId.referenced");
            referencedWarning.addParam(quizIdQuestion.getQuestionId());
            return referencedWarning;
        }
        return null;
    }

    /*public void insertQuizFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:quizzes.json").getInputStream();

        Map<String, List<?>> data = objectMapper.readValue(inputStream, new TypeReference<Map<String, List<?>>>() {});

        List<Quiz> quizzes = new ArrayList<>();
        for (Map.Entry<String, List<?>> entry : data.entrySet()) {
            if ("quizzes".equals(entry.getKey())) {
                for (Object obj : entry.getValue()) {
                    QuizDTO quizDTO = objectMapper.convertValue(obj, QuizDTO.class);
                    Quiz quiz = convertToQuiz(quizDTO);
                    quizzes.add(quiz);
                }
            }
        }
        // Enregistrez la liste des objets Sector dans la base de données
        quizRepository.saveAll(quizzes);
    }

    private Quiz convertToQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizDTO.getQuizId());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDuration(quizDTO.getDuration());
        quiz.setQuestions(quizDTO.getQuestions());
        return quiz;
    }*/

    /*public void insertQuizFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:quizzes.json").getInputStream();

        // Lire directement la liste des QuizDTO depuis le fichier JSON
        List<QuizDTO> quizDTOList = objectMapper.readValue(inputStream, new TypeReference<List<QuizDTO>>() {});

        // Convertir chaque QuizDTO en Quiz et les ajouter à une liste
        List<Quiz> quizzes = quizDTOList.stream()
                .map(this::convertToQuiz)
                .collect(Collectors.toList());

        // Enregistrer la liste des Quiz dans la base de données
        quizRepository.saveAll(quizzes);
    }

    private Quiz convertToQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizDTO.getQuizId());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDuration(quizDTO.getDuration());
        // Assurez-vous que la conversion de Set<Question> en un autre type si nécessaire est correctement gérée
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            // Convertit la liste des ID de questions en un ensemble de questions
            Set<Question> questions = quizDTO.getQuestions().stream()
                    .map(questionId -> questionRepository.findById(questionId)
                            .orElseThrow(() -> new NotFoundException("Question not found with id: " + questionId)))
                    .collect(Collectors.toSet());
            quiz.setQuestions(questions);
        }
        return quiz;
    }*/

    public void insertQuizFromJson() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStream = resourceLoader.getResource("classpath:quizzes.json").getInputStream();

        // Lire directement la liste des QuizDTO depuis le fichier JSON
        List<QuizDTO> quizDTOList = objectMapper.readValue(inputStream, new TypeReference<List<QuizDTO>>() {});

        // Convertir chaque QuizDTO en Quiz et les ajouter à une liste
        List<Quiz> quizzes = quizDTOList.stream()
                .map(this::convertToQuiz)
                .collect(Collectors.toList());

        // Enregistrer la liste des Quiz dans la base de données
        quizRepository.saveAll(quizzes);
    }

    private Quiz convertToQuiz(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setQuizId(quizDTO.getQuizId());
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        quiz.setDuration(quizDTO.getDuration());
        // Assurez-vous que la conversion de Set<Question> en un autre type si nécessaire est correctement gérée
        if (quizDTO.getQuestions() != null && !quizDTO.getQuestions().isEmpty()) {
            // Convertit la liste des ID de questions en un ensemble de questions
            Set<Question> questions = quizDTO.getQuestions().stream()
                    .map(questionId -> questionRepository.findById(questionId)
                            .orElseThrow(() -> new NotFoundException("Question not found with id: " + questionId)))
                    .collect(Collectors.toSet());
            quiz.setQuestions(questions);
        }
        return quiz;
    }

    public void assignRandomQuestionsToQuiz(Long quizId) {
        // Récupérer le quiz correspondant à partir de son identifiant
        /*Quiz quiz = quizRepository.findById(quizId).orElse(null);

        // Vérifier si le quiz existe
        if (quiz != null) {
            // Récupérer la liste complète de toutes les questions disponibles
            List<Question> allQuestions = questionRepository.findAll();

            // Assigner des questions aléatoires à ce quiz spécifique
            List<Question> randomQuestions = getRandomQuestions(allQuestions, quiz.getNumberOfQuestionsToAssign());
            // Convertir la liste en un ensemble avant de la définir sur l'attribut questions
            Set<Question> questionsSet = new HashSet<>(randomQuestions);
            quiz.setQuestions(questionsSet);

            // Enregistrer le quiz mis à jour dans la base de données
            quizRepository.save(quiz);
        } else {
            // Gérer le cas où le quiz n'existe pas (par exemple, lancer une exception ou effectuer un traitement approprié)
            // Ici, nous affichons simplement un message d'erreur
            System.out.println("Le quiz avec l'ID " + quizId + " n'existe pas.");
        }
        */
        Quiz quiz = quizRepository.findById(quizId).orElse(null);
        if (quiz != null) {
            List<Question> allQuestions = questionRepository.findAll();
            List<Question> randomQuestions = getRandomQuestions(allQuestions, quiz.getNumberOfQuestionsToAssign());

            // Assigner l'identifiant du quiz à chaque question affectée
            for (Question question : randomQuestions) {
                question.setQuizId(quiz);
            }

            // Convertir la liste en un ensemble avant de la définir sur l'attribut questions
            Set<Question> questionsSet = new HashSet<>(randomQuestions);
            quiz.setQuestions(questionsSet);

            // Enregistrer le quiz mis à jour dans la base de données
            quizRepository.save(quiz);

            // Enregistrer également chaque question avec son quizId dans la base de données
            questionRepository.saveAll(randomQuestions);
        } else {
            System.out.println("Le quiz avec l'ID " + quizId + " n'existe pas.");
        }
    }

    // Méthode pour obtenir un sous-ensemble aléatoire de questions à partir de la liste complète de questions
    private List<Question> getRandomQuestions(List<Question> questions, int numberOfQuestions) {
        List<Question> randomQuestions = new ArrayList<>(questions);
        Collections.shuffle(randomQuestions);
        return randomQuestions.subList(0, Math.min(numberOfQuestions, randomQuestions.size()));
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz getQuizById(Long quizId) {
        Optional<Quiz> quizOptional = quizRepository.findById(quizId);
        return quizOptional.orElse(null);
    }

    public void assignBadgesToQuiz(Long quizId, Set<Badge> badges) {
        // 1. Récupérer le quiz correspondant à l'ID fourni
        Optional<Quiz> optionalQuiz = quizRepository.findById(quizId);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();

            // 2. Mettre à jour la liste des badges associés au quiz
            quiz.setBadgeList(badges);

            // 3. Enregistrer le quiz mis à jour
            quizRepository.save(quiz);
        } else {
            throw new NoSuchElementException("Quiz not found for ID: " + quizId);
        }
    }
}
