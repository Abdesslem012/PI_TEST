package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter

public class LessonDTO {

    private Long lessonId;

    @NotNull
    @Size(max = 255)
    private String description;

    @LessonRatingIdUnique
    private Long ratingId;

    private Long programmeId;

    private Long unitId;

    private Set<Long> ressourcess;


}
