package com.example.demo.DTO;


import com.example.demo.Entity.Student;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class GroupeStudentDTO {

    private Long groupstudentId;

    @NotNull
    @Size(max = 255)
    private String nomGroupe;

    private Set<Student> studentId;

}
