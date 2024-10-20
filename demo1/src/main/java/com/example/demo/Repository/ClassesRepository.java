package com.example.demo.Repository;


import com.example.demo.Entity.Classes;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ClassesRepository extends MongoRepository<Classes, String> {
    Optional<Classes> findByName(String name);

}
