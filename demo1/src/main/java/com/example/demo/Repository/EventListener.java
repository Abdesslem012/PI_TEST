package com.example.demo.Repository;

import com.example.demo.Entity.Event;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class EventListener extends AbstractMongoEventListener<Event> {

    private final PrimarySequenceService primarySequenceService;

    public EventListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Event> event) {
        if (event.getSource().getEventId() == null) {
            event.getSource().setEventId(primarySequenceService.getNextValue());
        }
    }

}
