package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class Rating {

    @Id
    private Long ratingId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String nom;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    private Number nombre;

    @Indexed
    @DocumentReference(lazy = true)
    private Quiz quizId;

}
