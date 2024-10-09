package com.example.demo.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    private String txtComment;
    private Date datePosted;
    private Long postId;
}
