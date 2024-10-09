package com.example.demo.DTO;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.example.demo.Service.SectorService;
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


/**
 * Validate that the specializationId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = SectorSpecializationIdUnique.SectorSpecializationIdUniqueValidator.class
)
public @interface SectorSpecializationIdUnique {

    String message() default "{Exists.sector.specializationId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class SectorSpecializationIdUniqueValidator implements ConstraintValidator<SectorSpecializationIdUnique, Long> {

        private final SectorService sectorService;
        private final HttpServletRequest request;

        public SectorSpecializationIdUniqueValidator(final SectorService sectorService,
                final HttpServletRequest request) {
            this.sectorService = sectorService;
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
            final String currentId = pathVariables.get("sectorId");
            if (currentId != null && value.equals(sectorService.get(Long.parseLong(currentId)).getSpecializationId())) {
                // value hasn't changed
                return true;
            }
            return !sectorService.specializationIdExists(value);
        }

    }

}
