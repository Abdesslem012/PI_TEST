package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RessourcesDTO {

    private Long ressourcesId;

    @NotNull
    private TypeRessources type;

    @NotNull
    @Size(max = 255)
    private String url;

    private Long lessonId;

}
