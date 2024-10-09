package com.example.demo.Entity;

import lombok.Getter;
import lombok.Setter;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Date;
import java.util.Map;
@Getter
@Setter
@Document
public class Comment {

    @Id
    private Long id;

    private String txtComment;

    private Date datePosted;

    @Getter
    @DocumentReference(lazy = true)
    private Post postId;



}
