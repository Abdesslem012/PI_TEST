package com.example.demo.Repository;

import com.example.demo.Entity.Quiz;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class QuizListener extends AbstractMongoEventListener<Quiz> {

    private final PrimarySequenceService primarySequenceService;

    public QuizListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Quiz> event) {
        if (event.getSource().getQuizId() == null) {
            event.getSource().setQuizId(primarySequenceService.getNextValue());
        }
    }

}
