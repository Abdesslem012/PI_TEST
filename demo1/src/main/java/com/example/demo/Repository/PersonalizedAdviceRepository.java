package com.example.demo.Repository;


import com.example.demo.Entity.MoitoringAcadimicObjectives;
import com.example.demo.Entity.PersonalizedAdvice;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PersonalizedAdviceRepository extends MongoRepository<PersonalizedAdvice, Long> {

    PersonalizedAdvice findFirstByMoitoringId(
            MoitoringAcadimicObjectives moitoringAcadimicObjectives);

}
