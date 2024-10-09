package com.example.demo.Repository;


import com.example.demo.Entity.GroupeStudent;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface GroupeStudentRepository extends MongoRepository<GroupeStudent, Long> {
    Optional<GroupeStudent> findByNomGroupe(String groupName);
}
