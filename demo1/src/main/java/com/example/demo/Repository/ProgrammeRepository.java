package com.example.demo.Repository;

import com.example.demo.Entity.Programme;
import com.example.demo.Entity.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ProgrammeRepository extends MongoRepository<Programme, Long> {

    Programme findFirstBySectorId(Sector sector);

}
