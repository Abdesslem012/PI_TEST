package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PresenceDTO {

    private Long presenceId;

    @NotNull
    @Size(max = 255)
    private String suiviCours;

    @NotNull
    private Boolean presence;

    private Long coursId;

    private Long studentId; // Champ pour stocker l'identifiant de l'étudiant
    private String studentName; // Champ pour stocker le nom de l'étudiant


}