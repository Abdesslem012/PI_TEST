package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.util.Set;


@Document
@Getter
@Setter
public class Student {

    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String firstName;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String lastName;

    @Indexed
    @Size(max = 255)
    private String email;

    @Indexed
    private LocalDate dateOfBirth;

    @Indexed
    @DocumentReference(lazy = true)
    private Classes classes;

    @Indexed
    @DocumentReference(lazy = true)
    private GroupeStudent groupestudentId;

    @Indexed
    @DocumentReference(lazy = true)
    private MoitoringAcadimicObjectives monitoringld;

    @Indexed
    @DocumentReference(lazy = true)
    private Sector sectorId;

    @Indexed
    @DocumentReference(lazy = true)
    private Set<Badge> earnedBadges; // Liste des badges gagnés par l'étudiant


}
