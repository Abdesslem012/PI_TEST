package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
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
public class MoitoringAcadimicObjectives {

    @Id
    private Long maoId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String deadline;

    @Indexed
    @NotNull
    private String subject;

    @Indexed
    @NotNull
    private double average;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'monitoringld' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Student studentId;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'objectives' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Objectives> monitoringId;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'moitoringId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<PersonalizedAdvice> personalizedAdvices;


}
