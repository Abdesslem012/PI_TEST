package com.example.demo.Repository;

import com.example.demo.Entity.GroupeStudent;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class GroupeStudentListener extends AbstractMongoEventListener<GroupeStudent> {

    private final PrimarySequenceService primarySequenceService;

    public GroupeStudentListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<GroupeStudent> event) {
        if (event.getSource().getGroupstudentId() == null) {
            event.getSource().setGroupstudentId(primarySequenceService.getNextValue());
        }
    }

}
