package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Getter
@Setter
public class QuestionDTO {

    private Long questionId;

    @NotNull
    @Size(max = 255)
    private String text;

    /*@NotNull
    @Size(max = 255)
    private String correctAnswer;*/

    @NotNull
    private List<String> possibleAnswers; // Liste des réponses possibles

    @NotNull
    private Integer correctAnswerIndex; // Index de la réponse correcte dans la liste des réponses possibles

    @DocumentReference(lazy = true)
    private Long quizId;

}
