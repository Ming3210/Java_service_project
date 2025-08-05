package ra.java_service_project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailValidatorImpl.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EmailValidator {
    String message() default "Email is already in use";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
