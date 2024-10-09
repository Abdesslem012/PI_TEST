package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
public class Iscription {

    @Id
    private Long idInscription;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Indexed
    @Size(max = 255)
    private String lastName;

    @Indexed(unique = true)
    @NotNull
    @Size(max = 255)
    private String email;

    @Indexed(unique = true)
    @NotNull
    @Size(max = 255)
    private String phone;

    @Indexed
    private LocalDate birthdate;

    @Indexed
    private String status;

    @Indexed
    private String eventId;

    @Indexed
    private String studentId;




}
