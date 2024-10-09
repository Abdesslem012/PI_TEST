package com.example.demo.DTO;

import com.example.demo.Service.StudentService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final StudentService studentService;

    public UniqueEmailValidator(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        // Utilisez le service pour vérifier si l'adresse e-mail est unique
        return email == null || studentService.isEmailUnique(email);
    }
}
