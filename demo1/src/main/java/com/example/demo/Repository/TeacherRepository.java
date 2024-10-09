package com.example.demo.Repository;

import com.example.demo.Entity.Teacher;
import com.example.demo.Entity.Unit;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRepository extends MongoRepository<Teacher, Long> {

    Teacher findFirstByUnitId(Unit unit);

}
