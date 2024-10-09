package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@Getter
@Setter
@Document
public class Forum {
    @Id
    private Long forumId;

    @DocumentReference(lazy = true, lookup = "{ 'forumId' : ?#{#self._id} }")
    @ReadOnlyProperty
    private Set<Post> postId;

}