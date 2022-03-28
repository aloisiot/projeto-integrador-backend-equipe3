package dh.projetointegradorctd.backend.model.storage;

import dh.projetointegradorctd.backend.model.storage.City;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CityTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void quandoNomeEPaisExistem() {
        City city = new City();
        city.setName("city-test");
        city.setCountry("XX");
        Set<ConstraintViolation<City>> violacoes = validator.validate(city);
        assertThat(violacoes.size()).isZero();
    }

    @Test
    public void quandoNomeOuPaisEmBranco(){
        City city = new City();
        city.setName(" ");
        city.setCountry(" ");
        Set<ConstraintViolation<City>> violacoes = validator.validate(city);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeOuPaisVazios(){
        City city = new City();
        city.setName("");
        city.setCountry("");
        Set<ConstraintViolation<City>> violacoes = validator.validate(city);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeOuPaisNulos(){
        City city = new City();
        city.setName(null);
        city.setCountry(null);
        Set<ConstraintViolation<City>> violacoes = validator.validate(city);
        assertThat(violacoes.size()).isNotZero();
    }
}