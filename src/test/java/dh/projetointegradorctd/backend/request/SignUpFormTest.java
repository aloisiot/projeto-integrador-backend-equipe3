package dh.projetointegradorctd.backend.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SignUpFormTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateEmail() {
        SignUpForm signUpForm = new SignUpForm();
        signUpForm.setEmail(" ");
        Set<ConstraintViolation<SignUpForm>> violations = validator.validate(signUpForm);

        violations = violations
                .stream()
                .filter(v -> v.getMessage().equals("email invalido"))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());

        signUpForm.setEmail("invalid-email");
        violations = validator.validate(signUpForm);
        violations = violations.stream()
                .filter(v -> v.getMessage().equals("email invalido"))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());

        signUpForm.setEmail(null);
        violations = validator.validate(signUpForm);
        violations = violations
                .stream()
                .filter(v -> v.getMessage().equals("O email deve ser especificado"))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());
    }
}