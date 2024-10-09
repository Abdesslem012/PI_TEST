package com.example.demo.Repository;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepository extends MongoRepository<Comment, Long> {
    Comment findFirstByPostId(Post post);
    @Query("{'postId': ?0}")
    List<Comment> findCommentsByPostId(Long postId);
}