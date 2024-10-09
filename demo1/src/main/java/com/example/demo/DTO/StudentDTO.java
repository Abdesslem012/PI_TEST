package com.example.demo.DTO;

import com.example.demo.Entity.Classes;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;


@Getter
@Setter
public class StudentDTO {

    private Long studentId;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    @UniqueEmail // Annotation de validation personnalisée pour l'adresse e-mail unique
    private String email;


    private LocalDate dateOfBirth;

    private String classes;

    private Long groupestudentId;

    //@StudentMonitoringldUnique
    private Long monitoringld;

    private Long sectorId;

    private Set<Long> earnedBadges; // Liste des badges gagnés par l'étudiant



}
