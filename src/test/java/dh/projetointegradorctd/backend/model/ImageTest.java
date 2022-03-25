package dh.projetointegradorctd.backend.model;

import dh.projetointegradorctd.backend.model.dataStorage.Image;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class ImageTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void quandoTituloEUrlExistem() {
        Image image = new Image();
        image.setTitle("imagem x");
        image.setUrl("http://0.0.0.0");
        Set<ConstraintViolation<Image>> violacoes = validator.validate(image);
        assertThat(violacoes.size()).isZero();
    }

    @Test
    public void quandoTituloOuUrlEmBranco(){
        Image image = new Image();
        image.setTitle(" ");
        image.setUrl(" ");
        Set<ConstraintViolation<Image>> violacoes = validator.validate(image);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoTituloOuUrlVazios(){
        Image image = new Image();
        image.setTitle("");
        image.setUrl("");
        Set<ConstraintViolation<Image>> violacoes = validator.validate(image);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoTituloOuUrlNulos(){
        Image image = new Image();
        image.setTitle(null);
        image.setUrl(null);
        Set<ConstraintViolation<Image>> violacoes = validator.validate(image);
        assertThat(violacoes.size()).isNotZero();
    }
}