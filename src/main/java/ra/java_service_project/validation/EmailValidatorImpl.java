package ra.java_service_project.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ra.java_service_project.repository.UserRepository;

@RequiredArgsConstructor
public class EmailValidatorImpl implements ConstraintValidator<EmailValidator, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) return true;

        return !userRepository.existsByEmail(email);
    }
}
