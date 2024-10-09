package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "quizId")
@Document
@Getter
@Setter
public class Quiz {

    @Id
    private Long quizId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String title;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @NotNull
    private Integer duration;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'quizId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Rating rating;

    @Indexed
    @DocumentReference(lazy = true)
    private Set<Question> questions;

    @Indexed
    @DocumentReference(lazy = true)
    private Set<Badge> badgeList;

    public int getNumberOfQuestionsToAssign() {
        // Supposons que chaque quiz ait un niveau de difficulté représenté par un entier de 1 à 5
        int difficultyLevel = 3; // Exemple de niveau de difficulté

        // Logique pour déterminer le nombre de questions en fonction du niveau de difficulté
        int numberOfQuestions;
        switch (difficultyLevel) {
            case 1:
                numberOfQuestions = 3;
                break;
            case 2:
                numberOfQuestions = 5;
                break;
            case 3:
                numberOfQuestions = 7;
                break;
            case 4:
                numberOfQuestions = 9;
                break;
            case 5:
                numberOfQuestions = 10;
                break;
            default:
                numberOfQuestions = 5; // Par défaut, assigne 5 questions
                break;
        }
        return numberOfQuestions;
    }

}
