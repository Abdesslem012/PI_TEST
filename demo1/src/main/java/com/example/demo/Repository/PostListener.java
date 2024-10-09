package com.example.demo.Repository;


import com.example.demo.Entity.Post;
import com.example.demo.Service.PrimarySequenceService;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class PostListener extends AbstractMongoEventListener<Post> {

    private final PrimarySequenceService primarySequenceService;

    public PostListener(final PrimarySequenceService primarySequenceService) {
        this.primarySequenceService = primarySequenceService;
    }

    @Override
    public void onBeforeConvert(final BeforeConvertEvent<Post> event) {
        if (event.getSource().getPostId() == null) {
            event.getSource().setPostId(primarySequenceService.getNextValue());
        }
    }

}