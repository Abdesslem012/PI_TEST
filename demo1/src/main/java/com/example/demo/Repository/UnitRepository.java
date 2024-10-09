package com.example.demo.Repository;

import com.example.demo.Entity.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UnitRepository extends MongoRepository<Unit, Long> {

}
