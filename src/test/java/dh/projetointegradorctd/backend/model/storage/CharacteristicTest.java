package dh.projetointegradorctd.backend.model.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CharacteristicTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void quandoNomeExistem() {
        Characteristic characteristic = new Characteristic();
        characteristic.setName("characteristic x");
        Set<ConstraintViolation<Characteristic>> violacoes = validator.validate(characteristic);
        assertThat(violacoes.size()).isZero();
    }

    @Test
    public void quandoNomeEmBranco(){
        Characteristic characteristic = new Characteristic();
        characteristic.setName(" ");
        Set<ConstraintViolation<Characteristic>> violacoes = validator.validate(characteristic);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeVazios(){
        Characteristic characteristic = new Characteristic();
        characteristic.setName("");
        Set<ConstraintViolation<Characteristic>> violacoes = validator.validate(characteristic);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeNulos(){
        Characteristic characteristic = new Characteristic();
        characteristic.setName(null);
        Set<ConstraintViolation<Characteristic>> violacoes = validator.validate(characteristic);
        assertThat(violacoes.size()).isNotZero();
    }
}