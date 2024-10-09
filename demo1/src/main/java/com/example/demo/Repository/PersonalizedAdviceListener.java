package com.example.demo.Repository;


import com.example.demo.Entity.PersonalizedAdvice;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class PersonalizedAdviceListener extends AbstractMongoEventListener<PersonalizedAdvice> {

    private final PrimarySequenceService primarySequenceService;

    public PersonalizedAdviceListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<PersonalizedAdvice> event) {
        if (event.getSource().getIdPersonalized() == null) {
            event.getSource().setIdPersonalized(primarySequenceService.getNextValue());
        }
    }

}
