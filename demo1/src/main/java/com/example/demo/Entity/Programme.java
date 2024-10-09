package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
public class Programme {

    @Id
    private Long programmeId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String name;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @NotNull
    private Date startDate;

    @Indexed
    private Date endDate;

    @Indexed
    @DocumentReference(lazy = true)
    private Sector sectorId;

    /*@Indexed
    @DocumentReference(lazy = true, lookup = "{ 'programmeId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Lesson> lessonId;*/
}
