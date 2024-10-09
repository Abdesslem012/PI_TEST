package com.example.demo.Repository;

import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Entity.Objectives;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ObjectivesRepository extends MongoRepository<Objectives, Long> {

    Objectives findFirstByObjectives(MoitoringAcadimicObjectives moitoringAcadimicObjectives);

}
