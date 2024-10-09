package com.example.demo.Repository;


import com.example.demo.Entity.Unit;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class UnitListener extends AbstractMongoEventListener<Unit> {

    private final PrimarySequenceService primarySequenceService;

    public UnitListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Unit> event) {
        if (event.getSource().getUnitId() == null) {
            event.getSource().setUnitId(primarySequenceService.getNextValue());
        }
    }

}
