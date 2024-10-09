package com.example.demo.Repository;



import com.example.demo.Entity.Specialization;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class SpecializationListener extends AbstractMongoEventListener<Specialization> {

    private final PrimarySequenceService primarySequenceService;

    public SpecializationListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Specialization> event) {
        if (event.getSource().getSpecializationId() == null) {
            event.getSource().setSpecializationId(primarySequenceService.getNextValue());
        }
    }

}
