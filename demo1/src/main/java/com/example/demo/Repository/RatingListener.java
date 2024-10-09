package com.example.demo.Repository;

import com.example.demo.Entity.Rating;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class RatingListener extends AbstractMongoEventListener<Rating> {

    private final PrimarySequenceService primarySequenceService;

    public RatingListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Rating> event) {
        if (event.getSource().getRatingId() == null) {
            event.getSource().setRatingId(primarySequenceService.getNextValue());
        }
    }

}
