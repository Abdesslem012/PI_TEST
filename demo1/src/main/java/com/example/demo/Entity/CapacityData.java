package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Getter
@Setter
public class CapacityData {
    private int maxParticipants;
    private int participantsCount;
}