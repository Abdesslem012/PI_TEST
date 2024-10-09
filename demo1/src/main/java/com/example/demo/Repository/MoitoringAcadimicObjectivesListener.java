package com.example.demo.Repository;

import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class MoitoringAcadimicObjectivesListener extends AbstractMongoEventListener<MoitoringAcadimicObjectives> {

    private final PrimarySequenceService primarySequenceService;

    public MoitoringAcadimicObjectivesListener(
            final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<MoitoringAcadimicObjectives> event) {
        if (event.getSource().getMaoId() == null) {
            event.getSource().setMaoId(primarySequenceService.getNextValue());
        }
    }

}
