package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Document
@Getter
@Setter
//@JsonIgnoreProperties("sectorIds") // Exclure la propriété sectorIds lors de la sérialisation JSON
public class Specialization {

    @Id
    private Long specializationId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String name;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @DocumentReference(lazy = true)
    @JsonProperty("sectorIds")
    //@JsonManagedReference
    private Set<Sector> sectorIds;

}
