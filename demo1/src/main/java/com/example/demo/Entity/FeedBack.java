package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class FeedBack {

    @Id
    private Long feedbackId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String commentaire;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String auteur;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String destinataire;

    @Indexed
    @DocumentReference(lazy = true)
    private Lesson lessonId;

}
