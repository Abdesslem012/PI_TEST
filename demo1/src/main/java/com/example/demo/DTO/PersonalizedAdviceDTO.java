package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class PersonalizedAdviceDTO {

    private Long idPersonalized;

    @NotNull
    @Size(max = 255)
    private String adviceText;

    @Size(max = 255)
    private String moitoringId;

}
