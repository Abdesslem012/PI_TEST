package com.example.demo.Repository;

import com.example.demo.Entity.FeedBack;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class FeedBackListener extends AbstractMongoEventListener<FeedBack> {

    private final PrimarySequenceService primarySequenceService;

    public FeedBackListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<FeedBack> event) {
        if (event.getSource().getFeedbackId() == null) {
            event.getSource().setFeedbackId(primarySequenceService.getNextValue());
        }
    }

}