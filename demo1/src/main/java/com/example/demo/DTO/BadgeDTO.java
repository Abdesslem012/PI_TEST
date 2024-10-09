package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
public class BadgeDTO {
    private Long badgeId;
    @Indexed
    private String name;
    @Indexed
    private String description;
}
