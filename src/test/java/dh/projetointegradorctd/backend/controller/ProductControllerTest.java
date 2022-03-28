package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Category;
import dh.projetointegradorctd.backend.model.storage.Characteristic;
import dh.projetointegradorctd.backend.model.storage.Image;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.CategoryRepository;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {
    private final String END_POINT = "products/";

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    public Product getValidProduto() {
        Product product = new Product();
        product.setName("product-teste");
        product.setDescription("descricao-teste");

        Category category = categoryRepository.save(new Category());
        product.setCategory(category);

        Image image = new Image();
        image.setTitle("image-test");
        image.setUrl("http://url.test/");
        product.setImages(List.of(image));

        Characteristic characteristic = new Characteristic();
        characteristic.setName("characteristic-test");
        product.setCharacteristics(List.of(characteristic));

        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isZero();

        return product;
    }

    // Persiste e retorna uma entidade Product Valida
    public Product getProdutoEntity() {
        return productRepository.save(getValidProduto());
    }

    @Test
    public void quandoCriar_entaoHttpStatus201() {
        Product product = getValidProduto();
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Product.class
        );
        product = response.getBody();
        assertNotNull(product.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void quandoCriarFalhar_entaoHttpStatus400 ()  {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Product product = getValidProduto();
        product.setId((long) 1);
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Product.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorId_entaoHttpStatus200() {
        Long id = getProdutoEntity().getId();
        ResponseEntity<Product> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                Product.class
        );
        Product product = response.getBody();
        assertNotNull(product.getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorIdFalhar_entaoNoContent() {
        // Quando o ID nao existe na base de dados
        long id =  - 1;
        ResponseEntity<Product> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT +  "/" + id ),
                Product.class
        );
        Product product = response.getBody();
        assertNull(product);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarTodos_entaoStatus200 () {
        getProdutoEntity(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(response.getBody().size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200() {
        Product product = getProdutoEntity();
        product.setDescription("atualizado-test");
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Product.class
        );
        assertEquals(response.getBody().getDescription(), "atualizado-test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Product product = getValidProduto();
        product.setId((long) - 1);
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Product.class
        );
        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }
}