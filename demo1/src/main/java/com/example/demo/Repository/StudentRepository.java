package com.example.demo.Repository;

import com.example.demo.Entity.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;


public interface StudentRepository extends MongoRepository<Student, Long> {

    Student findFirstByClasses(Classes classes);

    Student findFirstByGroupestudentId(GroupeStudent groupeStudent);

    Student findFirstByMonitoringld(MoitoringAcadimicObjectives moitoringAcadimicObjectives);

    Student findFirstBySectorId(Sector sector);

    boolean existsByMonitoringldMaoId(Long maoId);

    Optional<Student> findByFirstNameAndLastName(String firstName, String lastName);





    @Query("{'email': ?0}")
    Student findByEmail(String email);


    Student findByFirstName(String firstName);
}
