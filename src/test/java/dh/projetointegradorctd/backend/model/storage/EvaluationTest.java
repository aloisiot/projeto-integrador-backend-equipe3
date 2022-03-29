package dh.projetointegradorctd.backend.model.storage;

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
class EvaluationTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void commentValidation() {
        Evaluation evaluation = new Evaluation();
        evaluation.setComment(" ");
        Set<ConstraintViolation<Evaluation>> violations = validator.validate(evaluation);
        Stream<ConstraintViolation<Evaluation>> commentemailVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("A avaliação deve conter um comentário"));

        assertThat(commentemailVionations.toArray().length).isEqualTo(1);

        evaluation.setComment(null);
        violations = validator.validate(evaluation);
        commentemailVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("A avaliação deve conter um comentário"));

        assertThat(commentemailVionations.toArray().length).isEqualTo(1);

        evaluation.setComment("Comentário válido");
        violations = validator.validate(evaluation);
        commentemailVionations = violations.stream().filter(
                violation ->
                        violation.getMessage().equals("A avaliação deve conter um comentário"));

        assertThat(commentemailVionations.toArray().length).isZero();
    }

    @Test
    public void starsValidation() {
        Evaluation evaluation = new Evaluation();
        evaluation.setStarts(6);
        Set<ConstraintViolation<Evaluation>> violations = validator.validate(evaluation);
        Stream<ConstraintViolation<Evaluation>> starsVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("O maximo de estrelas é 5"));

        assertThat(starsVionations.toArray().length).isEqualTo(1);

        evaluation.setStarts(-1);
        violations = validator.validate(evaluation);
        starsVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("O minimo de estrelas é 0"));

        assertThat(starsVionations.toArray().length).isEqualTo(1);

        evaluation.setStarts(2);
        violations = validator.validate(evaluation);
        starsVionations = violations.stream().filter(
                violation ->
                        violation.getMessage().equals("O minimo de estrelas é 0") ||
                        violation.getMessage().equals("O maximo de estrelas é 5") ||
                        violation.getMessage().equals("A avaliação deve conter uma quantidade de estrelas")
        );

        assertThat(starsVionations.toArray().length).isZero();
    }

    @Test
    public void productAndClientValidation() {
        Evaluation evaluation = new Evaluation();
        Set<ConstraintViolation<Evaluation>> violations = validator.validate(evaluation);
        Stream<ConstraintViolation<Evaluation>> starsVionations = violations.stream().filter(
                violation ->
                        violation.getMessage().equals("A avaliacao deve fazer referência a um produto") ||
                        violation.getMessage().equals("A avaliacao deve fazer referência a um cliente")
        );

        assertThat(starsVionations.toArray().length).isEqualTo(2);
    }
}