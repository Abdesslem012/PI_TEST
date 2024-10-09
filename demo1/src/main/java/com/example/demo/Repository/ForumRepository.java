package com.example.demo.Repository;

import com.example.demo.Entity.Forum;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ForumRepository extends MongoRepository<Forum, Long> {
}
