package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter

public class Lesson {

    @Id
    private Long lessonId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @DocumentReference(lazy = true)
    private Rating ratingId;

    @Indexed
    @DocumentReference(lazy = true)
    private Programme programmeId;

    @Indexed
    @DocumentReference(lazy = true)
    private Unit unitId;

    @Indexed
    @DocumentReference(lazy = true)
    //@ReadOnlyProperty
    private Set<Ressources> ressourcess;

}
