package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document
public class NotificationData {

    @Indexed
    private String eventId;

    @Indexed
    private String studentId;

    @Getter
    @Indexed
    private Long idInscription;

    public void setIdInscription(Long idInscription) {
        this.idInscription = idInscription;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}

