package com.example.demo.Service;





import java.util.List;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Entity.CapacityData;
import com.example.demo.Entity.Event;
import com.example.demo.Entity.Registration;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.util.MaxParticipantsReachedException;
import com.example.demo.util.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;


@Service
public class EventService {

    private final EventRepository eventRepository;

    private final StudentRepository studentRepository;

    @Autowired
    private NotificationService notificationService;

    public EventService(final EventRepository eventRepository,
                         StudentRepository studentRepository) {
        this.eventRepository = eventRepository;
        this.studentRepository = studentRepository;
    }

    public List<EventDTO> findAll() {
        final List<Event> events = eventRepository.findAll(Sort.by("eventId"));
        return events.stream()
                .map(event -> mapToDTO(event, new EventDTO()))
                .toList();
    }

    public EventDTO get(final Long eventId) {
        return eventRepository.findById(eventId)
                .map(event -> mapToDTO(event, new EventDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final EventDTO eventDTO) {
        final Event event = new Event();
        mapToEntity(eventDTO, event);
        return eventRepository.save(event).getEventId();
    }

    public void update(final Long eventId, final EventDTO eventDTO) {
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(eventDTO, event);
        eventRepository.save(event);
    }

    public void delete(final Long eventId) {
        final Event event = eventRepository.findById(eventId)
                .orElseThrow(NotFoundException::new);
        // remove many-to-many relations at owning side
       /* userRepository.findAllByEvents(event)
                .forEach(user -> user.getEvents().remove(event));*/
        eventRepository.delete(event);
    }

    private EventDTO mapToDTO(final Event event, final EventDTO eventDTO) {
        eventDTO.setEventId(event.getEventId());
        eventDTO.setNomEvent(event.getNomEvent());
        eventDTO.setDescription(event.getDescription());
        eventDTO.setDateDebut(event.getDateDebut());
        eventDTO.setDateFin(event.getDateFin());
        eventDTO.setLieu(event.getLieu());
        eventDTO.setSalle(event.getSalle());
        eventDTO.setMaxParticipants(event.getMaxParticipants());
        return eventDTO;
    }

    private Event mapToEntity(final EventDTO eventDTO, final Event event) {
        event.setNomEvent(eventDTO.getNomEvent());
        event.setDescription(eventDTO.getDescription());
        event.setDateDebut(eventDTO.getDateDebut());
        event.setDateFin(eventDTO.getDateFin());
        event.setLieu(eventDTO.getLieu());
        event.setSalle(eventDTO.getSalle());
        event.setMaxParticipants(eventDTO.getMaxParticipants());
        return event;
    }

    public void registerStudentForEvent(String eventName, String firstName) {
        Event event = eventRepository.findByNomEvent(eventName);
        if (event == null) {
            throw new jakarta.persistence.EntityNotFoundException("Event not found");
        }

        // Vérifier le nombre de participants actuels par rapport à maxParticipants
        if (event.isComplet()) {
            throw new MaxParticipantsReachedException("Maximum participants reached for this event");
        }

        // Recherche de l'étudiant par son prénom
        Student student = studentRepository.findByFirstName(firstName);
        if (student == null) {
            throw new jakarta.persistence.EntityNotFoundException("Student not found");
        }

        // Logique pour inscrire l'étudiant à l'événement
        Registration registration = new Registration();
        registration.setEvent(event);
        registration.setStudent(student);
        event.getRegistrations().add(registration);
        eventRepository.save(event);

        // Mise à jour des places restantes après l'inscription
        updateRemainingSpots(event.getEventId());
    }


    public int updateRemainingSpots(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Event not found"));

        int remainingSpots = event.getMaxParticipants() - event.getRegistrations().size();
        event.setRemainingSpots(Math.max(0, remainingSpots));
        eventRepository.save(event);
        return remainingSpots; // Retourner le nombre de places restantes
    }


/*
    public void markAttendance(Long eventId, Long studentId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Event not found"));

        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new javax.persistence.EntityNotFoundException("Student not found"));

        Attendance attendance = new Attendance();
        attendance.setEvent(event);
        attendance.setStudent(student);
        event.getAttendances().add(attendance);
        eventRepository.save(event);

        notificationService.sendNotification(student.getEmail(), "Présence à un événement",
                "Votre présence a été enregistrée à l'événement " + event.getNomEvent() + ".");
    }*/

    // Méthode pour obtenir un événement par son ID
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Event not found"));
    }

    // Méthode pour obtenir un étudiant par son ID
    public Student getStudentById(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Student not found"));
    }


    public CapacityData getEventCapacity(Long eventId) {
        // Logique pour récupérer les données de capacité de l'événement avec eventId depuis la base de données
        Event event = eventRepository.findById(eventId).orElse(null);
        if (event != null) {
            CapacityData capacityData = new CapacityData();
            capacityData.setMaxParticipants(event.getMaxParticipants());
            capacityData.setParticipantsCount(event.getMaxParticipants()); // Vous devez implémenter cette logique dans votre Event entity
            return capacityData;
        } else {
            throw new NotFoundException("Event not found with ID: " + eventId);
        }
    }


    public Integer getAvailableSeats(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Event not found with id: " + eventId));

        // Calcul du nombre de places disponibles
        Integer availableSeats = event.getMaxParticipants() - event.getRegistrations().size();
        return availableSeats;
    }

    @Transactional
    public void decreaseAvailableSeats(Long eventId, Integer seatsToDecrease) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException("Event not found"));
        int currentAvailableSeats = event.getMaxParticipants();
        if (currentAvailableSeats >= seatsToDecrease) {
            event.setMaxParticipants(currentAvailableSeats - seatsToDecrease);
            eventRepository.save(event);
        } else {
            throw new IllegalArgumentException("Not enough available seats");
        }
    }

}
