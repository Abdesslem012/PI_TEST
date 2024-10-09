package com.example.demo.Repository;

import com.example.demo.Entity.Badge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BadgeRepository extends MongoRepository<Badge, Long> {

}
