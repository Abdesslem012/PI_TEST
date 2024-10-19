package com.example.demo;

import com.example.demo.DTO.EventDTO;
import com.example.demo.Entity.Event;
import com.example.demo.Entity.Registration;
import com.example.demo.Entity.Student;
import com.example.demo.Repository.EventRepository;
import com.example.demo.Repository.StudentRepository;
import com.example.demo.Service.EventService;
import com.example.demo.util.MaxParticipantsReachedException;
import com.example.demo.util.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Ajoutez des tests pour la méthode findAll()
    }

    @Test
    void testGetEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setEventId(eventId);
        event.setNomEvent("Test Event");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        EventDTO eventDTO = eventService.get(eventId);
        assertEquals(eventId, eventDTO.getEventId());
        assertEquals("Test Event", eventDTO.getNomEvent());
    }

    @Test
    void testGetEventNotFound() {
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> eventService.get(eventId));
    }

    @Test
    void testCreateEvent() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setNomEvent("New Event");

        Event event = new Event();
        event.setEventId(1L);
        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Long createdEventId = eventService.create(eventDTO);
        assertEquals(1L, createdEventId);
    }

    @Test
    void testUpdateEvent() {
        Long eventId = 1L;
        EventDTO eventDTO = new EventDTO();
        eventDTO.setNomEvent("Updated Event");

        Event event = new Event();
        event.setEventId(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.update(eventId, eventDTO);

        assertEquals("Updated Event", event.getNomEvent());
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void testDeleteEvent() {
        Long eventId = 1L;
        Event event = new Event();
        event.setEventId(eventId);
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        eventService.delete(eventId);

        verify(eventRepository, times(1)).delete(event);
    }

    @Test
    void testRegisterStudentForEvent() {
        // Configuration des mocks pour tester l'inscription d'un étudiant
        String eventName = "Test Event";
        String firstName = "John";

        Event event = new Event();
        event.setNomEvent(eventName);
        event.setMaxParticipants(2);
        event.getRegistrations().add(new Registration()); // Ajoutez une inscription pour simuler une place déjà prise

        Student student = new Student();
        student.setFirstName(firstName);

        when(eventRepository.findByNomEvent(eventName)).thenReturn(event);
        when(studentRepository.findByFirstName(firstName)).thenReturn(student);

        eventService.registerStudentForEvent(eventName, firstName);

        assertEquals(1, event.getRegistrations().size());
    }

    @Test
    void testRegisterStudentForEventMaxParticipantsReached() {
        String eventName = "Full Event";
        String firstName = "John";

        Event event = new Event();
        event.setNomEvent(eventName);
        event.setMaxParticipants(1);
        event.getRegistrations().add(new Registration()); // Une inscription existante

        when(eventRepository.findByNomEvent(eventName)).thenReturn(event);
        when(studentRepository.findByFirstName(firstName)).thenReturn(new Student());

        assertThrows(MaxParticipantsReachedException.class, () -> eventService.registerStudentForEvent(eventName, firstName));
    }

    @Test
    void testUpdateRemainingSpots() {
        Long eventId = 1L;
        Event event = new Event();
        event.setMaxParticipants(10);
        event.getRegistrations().add(new Registration());
        event.getRegistrations().add(new Registration());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        int remainingSpots = eventService.updateRemainingSpots(eventId);
        assertEquals(8, remainingSpots);
        verify(eventRepository, times(1)).save(event);
    }

    // Ajoutez d'autres tests selon vos besoins...
}
