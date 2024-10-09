package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Document
@Getter
@Setter
public class Question {

    @Id
    private Long questionId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String text;

    /*@Indexed
    @NotNull
    @Size(max = 255)
    private String correctAnswer;*/

    @Indexed
    @NotNull
    private List<String> possibleAnswers; // Liste des réponses possibles

    @Indexed
    @NotNull
    private Integer correctAnswerIndex; // Index de la réponse correcte dans la liste des réponses possibles

    @Indexed
    @DocumentReference(lazy = true)
    private Quiz quizId;

}
