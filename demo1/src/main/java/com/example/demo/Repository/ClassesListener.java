/*package com.example.demo.Repository;


import com.example.demo.Entity.Classes;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ClassesListener extends AbstractMongoEventListener<Classes> {

    private final PrimarySequenceService primarySequenceService;

    public ClassesListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Classes> event) {
        if (event.getSource().getClasseId() == null) {
            event.getSource().setClasseId(primarySequenceService.getNextValue());
        }
    }

}*/
