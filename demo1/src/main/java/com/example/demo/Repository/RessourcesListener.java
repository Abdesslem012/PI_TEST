package com.example.demo.Repository;

import com.example.demo.Entity.Ressources;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class RessourcesListener extends AbstractMongoEventListener<Ressources> {

    private final PrimarySequenceService primarySequenceService;

    public RessourcesListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Ressources> event) {
        if (event.getSource().getRessourcesId() == null) {
            event.getSource().setRessourcesId(primarySequenceService.getNextValue());
        }
    }

}
