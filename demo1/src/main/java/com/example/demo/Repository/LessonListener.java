package com.example.demo.Repository;

import com.example.demo.Entity.Lesson;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class LessonListener extends AbstractMongoEventListener<Lesson> {

    private final PrimarySequenceService primarySequenceService;

    public LessonListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Lesson> event) {
        if (event.getSource().getLessonId() == null) {
            event.getSource().setLessonId(primarySequenceService.getNextValue());
        }
    }

}
