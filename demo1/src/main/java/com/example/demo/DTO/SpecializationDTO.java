package com.example.demo.DTO;

import com.example.demo.Entity.Sector;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
public class SpecializationDTO {

    private Long specializationId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @JsonProperty("sectorIds")
    //@JsonIgnore
    private Set<Sector> sectorIds;

    public SpecializationDTO() {
    }

}
