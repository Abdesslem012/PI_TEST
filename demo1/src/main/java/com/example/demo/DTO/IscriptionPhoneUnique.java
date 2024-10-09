 package com.example.demo.DTO;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

import com.example.demo.Service.IscriptionService;
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
        validatedBy = IscriptionPhoneUnique.IscriptionPhoneUniqueValidator.class
)
public @interface IscriptionPhoneUnique {

    String message() default "{Exists.iscription.phone}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class IscriptionPhoneUniqueValidator implements ConstraintValidator<IscriptionPhoneUnique, String> {

        private final IscriptionService iscriptionService;
        private final HttpServletRequest request;

        public IscriptionPhoneUniqueValidator(final IscriptionService iscriptionService,
                final HttpServletRequest request) {
            this.iscriptionService = iscriptionService;
            this.request = request;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext cvContext) {
            if (value == null) {
                // no value present
                return true;
            }
            @SuppressWarnings("unchecked") final Map<String, String> pathVariables =
                    ((Map<String, String>)request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE));
            final String currentId = pathVariables.get("idInscription");
            if (currentId != null && value.equalsIgnoreCase(iscriptionService.get(Long.parseLong(currentId)).getPhone())) {
                // value hasn't changed
                return true;
            }
            return !iscriptionService.phoneExists(value);
        }

    }

}
