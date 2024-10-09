package com.example.demo.Repository;

import com.example.demo.Entity.Programme;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ProgrammeListener extends AbstractMongoEventListener<Programme> {

    private final PrimarySequenceService primarySequenceService;

    public ProgrammeListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Programme> event) {
        if (event.getSource().getProgrammeId() == null) {
            event.getSource().setProgrammeId(primarySequenceService.getNextValue());
        }
    }

}
