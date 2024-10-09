package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Getter
@Setter
public class ProgrammeDTO {

    private Long programmeId;

    @NotNull
    @Size(max = 255)
    private String name;

    @NotNull
    @Size(max = 255)
    private String description;

    @NotNull
    private Date startDate;

    private Date endDate;

    private Long sectorId;

    //private Set<Long> lessonId;
}
