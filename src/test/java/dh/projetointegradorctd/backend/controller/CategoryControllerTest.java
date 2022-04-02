package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Category;
import dh.projetointegradorctd.backend.repository.CategoryRepository;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
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
import java.util.Objects;
import java.util.Set;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class CategoryControllerTest {

    @Value("${api-base-path}/categories/")
    private String END_POINT;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @LocalServerPort
    private int serverPort;

    private Category validCategoriaFactory() {
        Category category = new Category();
        category.setDescription("description-test");
        category.setQualification("qualification-test");
        category.setUrlImage("http://valid-url.com");

        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Category>> violations = validator.validate(category);
        assertEquals(0, violations.size());

        return category;
    }

    // Persiste e retorna uma entidade Category
    private Category categoriaEntityFactory() {
        return categoryRepository.save(validCategoriaFactory());
    }

    @Test
    public void whenCreate_thenHttpStatus201() {
        HttpEntity<Category> entity = new HttpEntity<>(validCategoriaFactory());
        ResponseEntity<Category> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Category.class
        );
        Category category = response.getBody();
        assert category != null;
        assertNotNull(category.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenCreateFails_whenHttpStatus422 () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Category category = new Category();
        category.setId((long) 1);
        HttpEntity<Category> entity = new HttpEntity<>(category);
        ResponseEntity<Category> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Category.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorId_entaoHttpStatus200() {
        Long id = categoriaEntityFactory().getId();
        ResponseEntity<Category> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                Category.class
        );
        Category category = response.getBody();
        assert category != null;
        assertNotNull(category.getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorIdFalhar_entaoNoContent() {
        // Quando o ID nao existe na base de dados
        long id = - 1;
        ResponseEntity<Category> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT +  "/" + id ),
                Category.class
        );
        Category category = response.getBody();
        assertNull(category);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarTodos_entaoStatus200 () {
        categoriaEntityFactory(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200() {
        Category category = categoriaEntityFactory();
        category.setDescription("atualizado-test");
        HttpEntity<Category> entity = new HttpEntity<>(category);
        ResponseEntity<Category> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Category.class
        );
        assertEquals(Objects.requireNonNull(response.getBody()).getDescription(), "atualizado-test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Category category = new Category();
        category.setId((long) - 1);
        HttpEntity<Category> entity = new HttpEntity<>(category);
        ResponseEntity<Category> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Category.class
        );
        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }
}