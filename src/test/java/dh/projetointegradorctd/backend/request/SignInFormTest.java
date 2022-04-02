package dh.projetointegradorctd.backend.request;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class SignInFormTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateEmail() {
        String emailIsEmpity = "O email deve ser especificado";
        String emailIsInvalid = "email invalido";

        SignInForm signInForm = new SignUpForm();
        signInForm.setEmail(" ");
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(signInForm);

        violations = violations
                .stream()
                .filter(v -> v.getMessage().equals(emailIsInvalid))
                .collect(Collectors.toSet());

        assertEquals(" ", signInForm.getEmail());
        assertEquals(1, violations.size());

        violations = validator.validate(signInForm);
        violations = violations.stream()
                .filter(v -> v.getMessage().equals(emailIsInvalid))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());

        signInForm.setEmail(null);
        violations = validator.validate(signInForm);
        violations = violations
                .stream()
                .filter(v -> v.getMessage().equals(emailIsEmpity))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());
    }

    @Test
    void validatePasswor() {
        SignInForm signInForm = new SignInForm();
        signInForm.setPassword("dasS");
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(signInForm);
        violations = violations.stream()
                .filter(v -> v.getMessage().equals("a senha deve tem no minimo oito caracters"))
                .collect(Collectors.toSet());

        assertEquals(1, violations.size());


        signInForm.setPassword("iKknclala8900");
        violations = validator.validate(signInForm);
        violations = violations.stream()
                .filter(v -> v.getMessage().equals("a senha deve tem no minimo oito caracters"))
                .collect(Collectors.toSet());

        assertEquals(0, violations.size());
    }
}
