package com.example.demo.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Document
public class Post {
    @Id
    private Long postId;


    private String title;

    private String txtPost;


    private LocalDate datePosted;
    @Indexed
    private String img  ;

    private  String postedBy;

    private  int likeCount;
    private  int viewCount;
    @JsonIgnore
    private List<String> tags;
    @JsonIgnore
    @DocumentReference(lazy = true)
    private Forum forumId;
    @DocumentReference(lazy = true, lookup = "{ 'postId' : ?#{#self._id} }")
    @ReadOnlyProperty
    @JsonIgnore
    private Set<Comment> commentId;
    // getters and setters

}