package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotNull
    private Long participantId;

    // Ajoutez d'autres champs nécessaires pour l'inscription
}

