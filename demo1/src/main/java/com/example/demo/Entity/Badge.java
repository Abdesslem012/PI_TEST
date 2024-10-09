package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Getter
@Setter
    public class Badge {
        @Id
        private Long badgeId;
        @Indexed
        private String name;
        @Indexed
        private String description;

    }

