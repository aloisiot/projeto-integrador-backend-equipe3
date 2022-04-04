package dh.projetointegradorctd.backend.model.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
public class UserTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void emailValidate() {
        User user = new User();
        user.setEmail("emailin-valido");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Stream<ConstraintViolation<User>> emailVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("email invalido"));

        assertThat(emailVionations.toArray().length).isNotZero();

        user.setEmail("user@server.com");
        violations = validator.validate(user);
        emailVionations = violations.stream()
                .filter(violation -> violation.getMessage().equals("email invalido"));

        assertThat(emailVionations.toArray().length).isZero();
    }

    @Test
    public void nameAndLastnameValidate() {
        User user = new User();
        user.setName("John");
        user.setLastname("Doe");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Stream<ConstraintViolation<User>> nameVionations = violations.stream().filter(
                violation ->
                        violation.getMessage().equals("O nome não pode ser nulo") ||
                        violation.getMessage().equals("O ultimo nome não deve estár em branco") ||
                        violation.getMessage().equals("Comprimento invalido para o campo nome") ||
                        violation.getMessage().equals("Comprimento invalido para o campo sobrenome")
        );
        assertThat(nameVionations.toArray().length).isZero();

        user.setName("a");
        user.setLastname("b");
        violations = validator.validate(user);
        nameVionations = violations.stream().filter(
                violation ->
                        violation.getMessage().equals("Comprimento invalido para o campo nome") ||
                        violation.getMessage().equals("Comprimento invalido para o campo sobrenome")
        );
        assertThat(nameVionations.toArray().length).isEqualTo(2);
    }

    @Test
    public void passwordValidation() {
        User user = new User();
        user.setPassword("1231");
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        Stream<ConstraintViolation<User>> passwordVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("A senha deve conter entre 8 e 255 caracters")
        );
        assertThat(passwordVionations.toArray().length).isEqualTo(1);

        user.setPassword(null);
        violations = validator.validate(user);
        passwordVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("A senha não deve estár em branco")
        );
        assertThat(passwordVionations.toArray().length).isEqualTo(1);
    }
}