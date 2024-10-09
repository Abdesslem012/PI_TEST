package com.example.demo.RestController;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Entity.CapacityData;
import com.example.demo.Entity.Event;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Service.EventService;
import com.example.demo.Service.NotificationService;
import com.example.demo.util.MaxParticipantsReachedException;
import com.example.demo.util.NotFoundException;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventController {
    private final EventService eventService;
    private  final NotificationService notificationService;
    private final EventRepository eventRepository;

    public EventController(final EventService eventService, NotificationService notificationService, EventRepository eventRepository) {
        this.eventService = eventService;
        this.notificationService = notificationService;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable(name = "eventId") final Long eventId) {
        return ResponseEntity.ok(eventService.get(eventId));
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvent(@RequestBody @Valid final EventDTO eventDTO) {
        final Long createdEventId = eventService.create(eventDTO);
        return new ResponseEntity<>(createdEventId, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{eventId}")
    public ResponseEntity<Long> updateEvent(@PathVariable(name = "eventId") final Long eventId,
                                            @RequestBody @Valid final EventDTO eventDTO) {
        eventService.update(eventId, eventDTO);
        return ResponseEntity.ok(eventId);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{eventId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvent(@PathVariable(name = "eventId") final Long eventId) {
        eventService.delete(eventId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/register/{eventName}/{firstName}")
    public ResponseEntity<?> registerStudentForEvent(@PathVariable("eventName") String eventName,
                                                     @PathVariable("firstName") String firstName) {
        try {
            eventService.registerStudentForEvent(eventName, firstName);
            // Mise à jour des places restantes après l'inscription
            eventService.updateRemainingSpots(Long.valueOf(eventName));
            // Récupérer le nombre de places disponibles mis à jour
            int remainingSpots = eventService.getAvailableSeats(Long.valueOf(eventName));
            return ResponseEntity.ok("Student registered for event. Remaining spots: " + remainingSpots);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event or student not found.");
        } catch (MaxParticipantsReachedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Maximum participants reached for this event.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }


    @GetMapping("/{eventId}/remainingSpots")
    public ResponseEntity<Integer> getRemainingSpots(@PathVariable Long eventId) {
        int remainingSpots = eventService.updateRemainingSpots(eventId);
        return ResponseEntity.ok(remainingSpots);
    }




/*
    @PutMapping("/{eventId}/attendance/{studentId}")
    public ResponseEntity<?> markAttendance(@PathVariable Long eventId, @PathVariable Long studentId) {
        eventService.markAttendance(eventId, studentId);
        notifyAttendance(eventId, studentId); // Appel de la méthode pour envoyer la notification de présence
        return ResponseEntity.ok("Attendance marked for student.");
    }*/

    // Méthode pour envoyer la notification d'inscription
    private void notifyRegistration(Long eventId, Long studentId) throws EntityNotFoundException {
        Event event = eventService.getEventById(eventId);
        Student student = eventService.getStudentById(studentId);
        notificationService.sendNotification(student.getEmail(), "Inscription à un événement",
                "Vous êtes inscrit à l'événement " + event.getNomEvent() + ".");
    }

    // Méthode pour envoyer la notification de présence
    private void notifyAttendance(Long eventId, Long studentId) {
        try {
            Event event = eventService.getEventById(eventId);
            Student student = eventService.getStudentById(studentId);
            notificationService.sendNotification(student.getEmail(), "Présence à un événement",
                    "Votre présence a été enregistrée à l'événement " + event.getNomEvent() + ".");
        } catch (EntityNotFoundException e) {
            // Gérer l'exception si l'événement ou l'étudiant n'est pas trouvé
            e.printStackTrace();
        }
    }

    @GetMapping("/event/{eventId}")
    public String showEventRatingPage(@PathVariable Long eventId) {
        // Logique pour afficher la page d'inscription avec l'ID de l'événement
        return "redirect:/allRating/" + eventId; // Redirection vers la page d'inscription avec l'ID de l'événement
    }

    @GetMapping("/{eventId}/capacity")
    public ResponseEntity<CapacityData> getEventCapacity(@PathVariable Long eventId) {
        Event event = eventService.getEventById(eventId);
        if (event != null) {
            CapacityData capacityData = new CapacityData();
            capacityData.setMaxParticipants(event.getMaxParticipants());
            capacityData.setParticipantsCount(event.getRemainingSpots()); // Utilisez la méthode getRemainingSpots()
            return ResponseEntity.ok(capacityData);
        } else {
            throw new NotFoundException("Event not found with ID: " + eventId);
        }
    }

    @GetMapping("/{eventId}/available-seats")
    public ResponseEntity<Integer> getAvailableSeats(@PathVariable Long eventId) {
        Integer availableSeats = eventService.getAvailableSeats(eventId);
        return ResponseEntity.ok(availableSeats);
    }

  /*  @PutMapping("/{eventId}/update-available-seats")
    public ResponseEntity<?> updateAvailableSeats(@PathVariable Long eventId, @RequestBody int seatsTaken) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found with id: " + eventId));

            int availableSeats = event.getAvailableSeats() - seatsTaken;
            if (availableSeats >= 0) {
                event.setAvailableSeats(availableSeats);
                eventRepository.save(event);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Not enough available seats");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with id: " + eventId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating available seats");
        }
    }*/

    @PutMapping("/{eventId}/update-available-seats")
    public ResponseEntity<?> updateAvailableSeats(@PathVariable Long eventId, @RequestBody int seatsChange) {
        try {
            Event event = eventRepository.findById(eventId)
                    .orElseThrow(() -> new NoSuchElementException("Event not found with id: " + eventId));

            int maxParticipants = event.getMaxParticipants();
            int updatedMaxParticipants = maxParticipants - seatsChange; // Réduction du maximum de participants
            if (updatedMaxParticipants >= 0) {
                event.setMaxParticipants(updatedMaxParticipants);
                eventRepository.save(event);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid seatsChange value");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event not found with id: " + eventId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating available seats");
        }
    }


}

