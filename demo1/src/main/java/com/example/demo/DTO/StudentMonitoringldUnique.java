package com.example.demo.DTO;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;


import com.example.demo.Service.StudentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Constraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;
import org.springframework.web.servlet.HandlerMapping;


@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = StudentMonitoringldUnique.StudentMonitoringldUniqueValidator.class
)
public @interface StudentMonitoringldUnique {

    String message() default "{Exists.student.monitoringld}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class StudentMonitoringldUniqueValidator implements ConstraintValidator<StudentMonitoringldUnique, Long> {

        private final StudentService studentService;
        private final HttpServletRequest request;

        public StudentMonitoringldUniqueValidator(final StudentService studentService,
                final HttpServletRequest request) {
            this.studentService = studentService;
            this.request = request;
        }

        @Override
        public boolean isValid(final Long value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables = 
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("studentId");
            if (currentId != null && value.equals(studentService.get(Long.parseLong(currentId)).getMonitoringld())) {
                // value hasn't changed
                return true;
            }
            return !studentService.monitoringldExists(value);
        }

    }

}
