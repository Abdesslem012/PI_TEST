package com.example.demo.DTO;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EventDTO {

    private Long eventId;

    @NotNull
    @Size(max = 255)
    private String nomEvent;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private LocalDateTime dateDebut;

    private LocalDateTime dateFin;

    private String lieu;
    private String salle;
    private Integer maxParticipants;



}
