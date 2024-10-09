package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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


@Document(collection ="Unit")
@Getter
@Setter
public class Unit {

    @Id
    private Long unitId;

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
    @Size(max = 255)
    private String content;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'unitId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Teacher> teachers;

    @Indexed
    @DocumentReference(lazy = true)
    //, lookup = "{ 'unitId' : ?#{#self._id} }"
    //@ReadOnlyProperty
    @JsonManagedReference // Gérer la sérialisation pour éviter la boucle infinie
    private Set<Lesson> lessons;

}
