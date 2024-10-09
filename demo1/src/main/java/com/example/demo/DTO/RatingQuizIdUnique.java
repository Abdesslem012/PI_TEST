package com.example.demo.DTO;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.example.demo.Service.RatingService;
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
 * Validate that the quizId value isn't taken yet.
 */
@Target({ FIELD, METHOD, ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(
        validatedBy = RatingQuizIdUnique.RatingQuizIdUniqueValidator.class
)
public @interface RatingQuizIdUnique {

    String message() default "{Exists.rating.quizId}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class RatingQuizIdUniqueValidator implements ConstraintValidator<RatingQuizIdUnique, Long> {

        private final RatingService ratingService;
        private final HttpServletRequest request;

        public RatingQuizIdUniqueValidator(final RatingService ratingService,
                final HttpServletRequest request) {
            this.ratingService = ratingService;
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
            final String currentId = pathVariables.get("ratingId");
            if (currentId != null && value.equals(ratingService.get(Long.parseLong(currentId)).getQuizId())) {
                // value hasn't changed
                return true;
            }
            return !ratingService.quizIdExists(value);
        }

    }

}
