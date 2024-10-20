package com.example.demo.Repository;

import com.example.demo.Entity.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SectorRepository extends MongoRepository<Sector, Long> {

    boolean existsBySpecializationIdSpecializationId(Long specializationId);

}
