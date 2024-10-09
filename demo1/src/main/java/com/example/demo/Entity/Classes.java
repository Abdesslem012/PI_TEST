package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;
import java.util.Set;


@Data
@Document(collection = "classes")
@Getter
@Setter
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "classeId")
public class Classes {

    @Id
    private String classeId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Indexed
    @Size(max = 255)
    private String description;

    @Indexed
    @DocumentReference(lazy = true, lookup = "{ 'classes' : ?#{#self._id} }")
    @ReadOnlyProperty
    @JsonIgnore
    private Set<Student> studentId;

    @JsonIgnore
    @Indexed
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;
    @DocumentReference(lazy = true)
    private List<Student> students;

    public List<Student> getStudents() {
        return students;
    }
}
