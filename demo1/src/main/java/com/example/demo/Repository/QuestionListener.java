package com.example.demo.Repository;

import com.example.demo.Entity.Question;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class QuestionListener extends AbstractMongoEventListener<Question> {

    private final PrimarySequenceService primarySequenceService;

    public QuestionListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Question> event) {
        if (event.getSource().getQuestionId() == null) {
            event.getSource().setQuestionId(primarySequenceService.getNextValue());
        }
    }

}
