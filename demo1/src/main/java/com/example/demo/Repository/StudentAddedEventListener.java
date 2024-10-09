package com.example.demo.Repository;


import com.example.demo.DTO.StudentAddedEvent;
import com.example.demo.Entity.Student;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class StudentAddedEventListener implements ApplicationListener<StudentAddedEvent> {

    @Override
    public void onApplicationEvent(StudentAddedEvent event) {
        // Logique pour gérer l'événement de l'ajout d'un étudiant à un groupe ou une classe
        // Par exemple, envoyer une notification, enregistrer des données, etc.
        Student student = event.getStudent();
        // Logique à ajouter ici
        System.out.println("Student added: " + student.getFirstName() + " " + student.getLastName());
    }
}