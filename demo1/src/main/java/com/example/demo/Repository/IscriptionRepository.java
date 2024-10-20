package com.example.demo.Repository;

import com.example.demo.Entity.Iscription;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface IscriptionRepository extends MongoRepository<Iscription, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneIgnoreCase(String phone);

    Iscription findByEventIdAndStudentId(String eventId, String studentId);
}
