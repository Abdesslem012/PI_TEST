package com.example.demo.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;


@Getter
@Setter
public class IscriptionDTO {

    private Long idInscription;

    @NotNull
    @Size(max = 255)
    private String firstName;

    @NotNull
    @Size(max = 255)
    private String lastName;

    @NotNull
    @Size(max = 255)
    //@IscriptionEmailUnique
    private String email;

    @NotNull
    @Size(max = 255)
   // @IscriptionPhoneUnique
    private String phone;

    private LocalDate birthdate;
    private String status;


    private String eventId;


    private String studentId;


}
