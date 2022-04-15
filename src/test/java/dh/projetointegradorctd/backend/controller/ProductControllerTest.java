package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.BackEndApplicationTests;
import dh.projetointegradorctd.backend.model.storage.*;
import dh.projetointegradorctd.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= WebEnvironment.RANDOM_PORT)
public class ProductControllerTest {

    @Value("${api-base-path}/products/")
    private String END_POINT;

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    public void setUp() {
        BackEndApplicationTests.setUp(
                cityRepository,
                categoryRepository,
                productRepository,
                clientRepository,
                roleRepository
        );
    }

    public Product validProdutoFactory() {
        Product product = new Product();
        product.setName("product-teste");
        product.setDescription("descricao-teste");

        Long categoryId = categoryRepository.getMaxId();
        Category category = new Category();
        category.setId(categoryId);
        product.setCategory(category);

        long cityId = cityRepository.getMaxId();
        City city = new City();
        city.setId(cityId);
        product.setCity(city);

        Image image = new Image();
        image.setTitle("image-test");
        image.setUrl("http://url.test/");
        product.setImages(List.of(image));

        Characteristic characteristic = new Characteristic();
        characteristic.setName("characteristic-test");
        characteristicRepository.save(characteristic);
        product.setCharacteristics(List.of(characteristic));

        ProductPolicies policies = new ProductPolicies();
        policies.setCancellation("cancellation policies");
        policies.setGeneralRules("general policies");
        policies.setCheersAndSecurity("cheers and security policies");
        product.setPolicies(policies);

        product.setAddress("address");

        Set<ConstraintViolation<Product>> violacoes = validator.validate(product);
        assertThat(violacoes.size()).isZero();

        return product;
    }

    // Persiste e retorna uma entidade Product Valida
    public Product produtoEntityFactory() {
        return productRepository.save(validProdutoFactory());
    }

    @Test
    public void whenCreate_thenHttpStatus201() {
        Product product = validProdutoFactory();
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Product.class
        );
        product = response.getBody();
        assert product != null;
        assertNotNull(product.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenCreateFails_whenHttpStatus422()  {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Product product = produtoEntityFactory();
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
        Long id = produtoEntityFactory().getId();
        ResponseEntity<Product> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                Product.class
        );
        Product product = response.getBody();
        assert product != null;
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
        produtoEntityFactory(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(Objects.requireNonNull(response.getBody()).size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200() {
        Product product = produtoEntityFactory();
        product.setDescription("atualizado-test");
        HttpEntity<Product> entity = new HttpEntity<>(product);
        ResponseEntity<Product> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Product.class
        );
        assertEquals(Objects.requireNonNull(response.getBody()).getDescription(), "atualizado-test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        Product product = validProdutoFactory();
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

    @Test
    public void whenFindAllByCity () {
        long cityId = cityRepository.getMaxId();
        String relativePath = END_POINT + "/by-city/"+ cityId;
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        boolean sucess = HttpStatus.OK == response.getStatusCode();
        boolean noContent = HttpStatus.NO_CONTENT == response.getStatusCode();
        assertTrue(sucess || noContent);
    }

    @Test
    public void whenFindAllByCategory_thenStatusOK () {
        long categoryId = categoryRepository.getMaxId();
        String relativePath = END_POINT + "by-category/" + categoryId;
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        assert response.getBody() != null : "A requisição falhou - corpo resposta nulo";
        assertTrue(response.hasBody());
        assertTrue(response.getBody().size() > 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void whenFindAllByCategoryFail_thenStatus204 () {
        long categoryId = categoryRepository.getMaxId() + 1;
        String relativePath = END_POINT + "by-category/" + categoryId;
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        assertFalse(response.hasBody());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void whenFindAllByAvailableDateRange () {
        long categoryId = categoryRepository.getMaxId();
        String relativePath = END_POINT + "by-available-date-range/2022-10-12/2022-10-14";
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        boolean sucess = HttpStatus.OK == response.getStatusCode();
        boolean noContent = HttpStatus.NO_CONTENT == response.getStatusCode();
        assertTrue(sucess || noContent);
    }

    @Test
    public void whenFindAllByAvailableDateRangeAndCity () {
        long cityId = cityRepository.getMaxId();
        String relativePath = END_POINT + "/by-city/"+ cityId +"/and-available-date-range/2022-10-12/2022-10-14";
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        boolean sucess = HttpStatus.OK == response.getStatusCode();
        boolean noContent = HttpStatus.NO_CONTENT == response.getStatusCode();
        assertTrue(sucess || noContent);
    }
}