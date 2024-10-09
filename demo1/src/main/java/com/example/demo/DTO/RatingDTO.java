package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatingDTO {

    private Long ratingId;

    @NotNull
    @Size(max = 255)
    private String nom;

    @NotNull
    @Size(max = 255)
    private String description;

    private Number nombre;

    @RatingQuizIdUnique
    private Long quizId;

}
