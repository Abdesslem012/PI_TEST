package com.example.demo.Repository;

import com.example.demo.Entity.Presence;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PresenceListener extends AbstractMongoEventListener<Presence> {

    private final PrimarySequenceService primarySequenceService;

    public PresenceListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Presence> event) {
        if (event.getSource().getPresenceId() == null) {
            event.getSource().setPresenceId(primarySequenceService.getNextValue());
        }
    }

}
