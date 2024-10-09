package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.*;
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
public class Sector {

    @Id
    private Long sectorId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String name;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'sectorId' : ?#{#self._id} }")
    @ReadOnlyProperty
    @JsonIgnore
    private Set<Student> students;

    @Indexed
    @DocumentReference(lazy = true)
    //@JsonProperty("specialites")
    @ReadOnlyProperty
    @JsonIgnore
    private Specialization specializationId;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'sectorId' : ?#{#self._id} }")
    @ReadOnlyProperty
    @JsonIgnore
    private Set<Programme> programmeId;

}
