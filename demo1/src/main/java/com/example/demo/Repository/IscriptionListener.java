package com.example.demo.Repository;


import com.example.demo.Entity.Iscription;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class IscriptionListener extends AbstractMongoEventListener<Iscription> {

    private final PrimarySequenceService primarySequenceService;

    public IscriptionListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Iscription> event) {
        if (event.getSource().getIdInscription() == null) {
            event.getSource().setIdInscription(primarySequenceService.getNextValue());
        }
    }

}
