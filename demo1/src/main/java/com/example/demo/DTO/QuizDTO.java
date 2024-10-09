package com.example.demo.DTO;

import com.example.demo.Entity.Rating;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;


@Getter
@Setter
public class QuizDTO {

    private Long quizId;

    @NotNull
    @Size(max = 255)
    private String title;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private Integer duration;

    @DocumentReference(lazy = true, lookup = "{ 'quizId' : ?#{#self._id} }")
    private Rating rating;

    //@DocumentReference(lazy = true)
    private Set<Long> questions;

    private Set<Long> badgeList;


}
