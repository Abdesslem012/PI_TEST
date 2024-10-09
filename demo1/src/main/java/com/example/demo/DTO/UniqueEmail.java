package com.example.demo.DTO;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
//@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueEmail {

    String message() default "Email must be unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}