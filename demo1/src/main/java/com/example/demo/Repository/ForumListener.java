package com.example.demo.Repository;


import com.example.demo.Entity.Forum;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class ForumListener extends AbstractMongoEventListener<Forum> {

    private final PrimarySequenceService primarySequenceService;

    public ForumListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Forum> event) {
        if (event.getSource().getForumId() == null) {
            event.getSource().setForumId(primarySequenceService.getNextValue());
        }
    }

}