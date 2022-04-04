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
public class ProductTest {

    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void quandoNomeEDescricaoExiste() {
        Product product = new Product();
        product.setCategory(new Category());
        product.setCity(new City());
        product.setName("product-test");
        product.setDescription("descricao-test");
        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isZero();
    }

    @Test
    public void quandoNomeOuDescricaoEmBranco(){
        Product product = new Product();
        product.setCategory(new Category());
        product.setName(" ");
        product.setDescription(" ");
        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeOuDescricaoVazio(){
        Product product = new Product();
        product.setCategory(new Category());
        product.setName("");
        product.setDescription("");
        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isNotZero();
    }

    @Test
    public void quandoNomeOuDescricaoNullo(){
        Product product = new Product();
        product.setCategory(new Category());
        product.setName(null);
        product.setDescription(null);
        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isNotZero();
    }
}