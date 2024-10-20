package com.example.demo.Repository;

import com.example.demo.Entity.Specialization;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SpecializationRepository extends MongoRepository<Specialization, Long> {
}
