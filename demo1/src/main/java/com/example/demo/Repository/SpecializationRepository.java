package com.example.demo.Repository;

import com.example.demo.Entity.Specialization;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface SpecializationRepository extends MongoRepository<Specialization, Long> {
}
