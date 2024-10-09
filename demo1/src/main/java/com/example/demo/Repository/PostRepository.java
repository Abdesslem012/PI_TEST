package com.example.demo.Repository;

import com.example.demo.Entity.Forum;
import com.example.demo.Entity.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Long> {
    Post findFirstByForumId(Forum forum);
}
