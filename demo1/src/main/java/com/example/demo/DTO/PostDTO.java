package com.example.demo.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class PostDTO {

    private Long postId;

    private String title;

    private String txtPost;

    private LocalDate datePosted;
    @JsonIgnore
    private Long forumId;

    private  String postedBy;
    private  String img;
    private  int likeCount;
    private  int viewCount;
    @JsonIgnore
    private List<String> tags;
}
