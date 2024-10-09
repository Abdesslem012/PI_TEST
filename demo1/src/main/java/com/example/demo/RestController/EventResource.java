 /*package com.example.demo.RestController;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Entity.Event;
import com.example.demo.Entity.Student;
import com.example.demo.Service.EventService;
import com.example.demo.Service.NotificationService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@CrossOrigin("http://localhost:4200/")
@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventResource {

    private final EventService eventService;
    private  final NotificationService notificationService;

    public EventResource(final EventService eventService, NotificationService notificationService) {
        this.eventService = eventService;
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable(name = "eventId") final Long eventId) {
        return ResponseEntity.ok(eventService.get(eventId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvent(@RequestBody @Valid final EventDTO eventDTO) {
        final Long createdEventId = eventService.create(eventDTO);
        return new ResponseEntity<>(createdEventId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Long> updateEvent(@PathVariable(name = "eventId") final Long eventId,
            @RequestBody @Valid final EventDTO eventDTO) {
        eventService.update(eventId, eventDTO);
        return ResponseEntity.ok(eventId);
    }

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
            return ResponseEntity.ok("Student registered for event.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Event or student not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error.");
        }
    }




   /* @PutMapping("/{eventId}/attendance/{studentId}")
    public ResponseEntity<?> markAttendance(@PathVariable Long eventId, @PathVariable Long studentId) {
        eventService.markAttendance(eventId, studentId);
        notifyAttendance(eventId, studentId); // Appel de la méthode pour envoyer la notification de présence
        return ResponseEntity.ok("Attendance marked for student.");
    }
*/



