package com.example.demo.DTO;

import com.example.demo.Entity.Student;
import org.springframework.context.ApplicationEvent;
import lombok.Getter;

@Getter
public class StudentAddedEvent extends ApplicationEvent {
    private final Student student;

    public StudentAddedEvent(Object source, Student student) {
        super(source);
        this.student = student;
    }
}
