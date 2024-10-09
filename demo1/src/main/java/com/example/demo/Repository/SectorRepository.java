package com.example.demo.Repository;

import com.example.demo.Entity.Sector;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface SectorRepository extends MongoRepository<Sector, Long> {

    boolean existsBySpecializationIdSpecializationId(Long specializationId);

}
