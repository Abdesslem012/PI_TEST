package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;


@Document
@Getter
@Setter
public class Presence {

    @Id
    private Long presenceId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String suiviCours;

    @Indexed
    @NotNull
    private Boolean presence;

    @Indexed
    @DocumentReference(lazy = true)
    private Unit unitId;

    @Indexed
    @DocumentReference(lazy = true)
    private Student student;


    @Indexed
    private List<Student> studentNames; // Correction du type en List<Student>

    // Getter et setter pour studentNames
    /*public List<Student> getStudentNames() {
        return studentNames;
    }

    public void setStudentNames(String studentNames) {
        this.studentNames = studentNames;
    }*/


}
