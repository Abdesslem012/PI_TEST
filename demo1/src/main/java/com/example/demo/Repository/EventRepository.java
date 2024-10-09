package com.example.demo.Repository;


import com.example.demo.Entity.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public interface EventRepository extends MongoRepository<Event, Long> {
    Event findByNomEvent(String eventName);
}
