package com.example.demo.Entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate; // Importez java.time.LocalDate pour utiliser LocalDate
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

<<<<<<< Updated upstream
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
=======
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
>>>>>>> Stashed changes


@Document
@Getter
@Setter
public class Event {

    @Id
    private Long eventId;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String nomEvent;

    @Indexed
    @NotNull
    @Size(max = 255)
    private String description;

    @Indexed
    @NotNull
    private LocalDateTime dateDebut;

    @Indexed
    private LocalDateTime dateFin;


    @Indexed
    private String lieu;
    @Indexed
    private String salle;
    @Indexed
    private Integer maxParticipants;


    @javax.persistence.OneToMany(mappedBy = "event", cascade = javax.persistence.CascadeType.ALL)
    private List<Registration> registrations = new ArrayList<>();
/*
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Attendance> attendances = new ArrayList<>();
*/

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }
/*
    public List<Attendance> getAttendances() {
        return attendances;
    }

    public void setAttendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }
*/

    private int remainingSpots;


    private int totalSeats;

    private int availableSeats;
    public boolean isComplet() {
        return remainingSpots <= 0;
    }

    public int getRemainingSpots() {
        return getMaxParticipants() - getRegistrations().size();
    }

}