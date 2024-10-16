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
public class GroupeStudent {

    @Id
    private Long groupstudentId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String nomGroupe;

    @Indexed
    @DocumentReference(lazy = true)
    //@ReadOnlyProperty
    private Set<Student> studentId;

}
