package com.example.demo.Repository;

import com.example.demo.Entity.Objectives;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ObjectivesListener extends AbstractMongoEventListener<Objectives> {

    private final PrimarySequenceService primarySequenceService;

    public ObjectivesListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Objectives> event) {
        if (event.getSource().getObjectivesId() == null) {
            event.getSource().setObjectivesId(primarySequenceService.getNextValue());
        }
    }

}
