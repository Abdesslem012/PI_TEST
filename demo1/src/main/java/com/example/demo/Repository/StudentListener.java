package com.example.demo.Repository;

import com.example.demo.Entity.Student;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class StudentListener extends AbstractMongoEventListener<Student> {

    private final PrimarySequenceService primarySequenceService;

    public StudentListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Student> event) {
        if (event.getSource().getStudentId() == null) {
            event.getSource().setStudentId(primarySequenceService.getNextValue());
        }
    }

}
