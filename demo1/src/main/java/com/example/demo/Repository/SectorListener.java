package com.example.demo.Repository;


import com.example.demo.Entity.Sector;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class SectorListener extends AbstractMongoEventListener<Sector> {

    private final PrimarySequenceService primarySequenceService;

    public SectorListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Sector> event) {
        if (event.getSource().getSectorId() == null) {
            event.getSource().setSectorId(primarySequenceService.getNextValue());
        }
    }

}
