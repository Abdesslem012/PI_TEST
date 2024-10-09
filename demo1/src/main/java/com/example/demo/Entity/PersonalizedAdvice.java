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
public class PersonalizedAdvice {

    @Id
    private Long idPersonalized;

    @NotNull
    @Indexed
    @Size(max = 255)
    private String adviceText;

    @Indexed
    @DocumentReference(lazy = true)
    private MoitoringAcadimicObjectives moitoringId;

}
