package com.example.demo.Entity;

import com.example.demo.DTO.TypeRessources;
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
public class Ressources {

    @Id
    private Long ressourcesId;

    @Indexed
    @NotNull
    private TypeRessources type;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String url;

    @Indexed
    @DocumentReference(lazy = true)
    private Lesson lessonId;

    public void setLesson(Lesson lesson) {
        this.lessonId = lesson;
    }
}
