package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedBackDTO {

    private Long feedbackId;

    @NotNull
    @Size(max = 255)
    private String commentaire;

    @NotNull
    @Size(max = 255)
    private String auteur;

    @NotNull
    @Size(max = 255)
    private String destinataire;

    private Long coursId;

}