package com.example.demo.Repository;

import com.example.demo.Entity.Teacher;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class TeacherListener extends AbstractMongoEventListener<Teacher> {

    private final PrimarySequenceService primarySequenceService;

    public TeacherListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Teacher> event) {
        if (event.getSource().getTeacherId() == null) {
            event.getSource().setTeacherId(primarySequenceService.getNextValue());
        }
    }

}
