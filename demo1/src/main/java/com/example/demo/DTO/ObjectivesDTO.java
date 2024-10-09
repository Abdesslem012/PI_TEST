package com.example.demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ObjectivesDTO {

    private Long objectivesId;

    @NotNull
    @Size(max = 255)
    private String description;

    private Long objectives;

}
