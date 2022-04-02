package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.City;
import dh.projetointegradorctd.backend.repository.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
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
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CityControllerTest {

    @Value("${api-base-path}/cities/")
    private String END_POINT;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CityRepository cityRepository;

    @LocalServerPort
    private int serverPort;

    private City getValidCidade() {
        City city = new City();
        city.setName("city-test");
        city.setCountry("XX");
        Set<ConstraintViolation<City>> violacoes = validator.validate(city);
        assertThat(violacoes.size()).isZero();
        return city;
    }

    // Persiste e retorna uma entidade City
    public City cidadeEntityFactory() {
        return cityRepository.save(getValidCidade());
    }

    @Test
    public void whenCreate_thenHttpStatus201() {
        HttpEntity<City> entity = new HttpEntity<>(getValidCidade());
        ResponseEntity<City> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                City.class
        );
        City city = response.getBody();
        assertNotNull(city.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenCreateFails_whenHttpStatus422 () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        City city = new City();
        city.setId((long) 1);
        HttpEntity<City> entity = new HttpEntity<>(city);
        ResponseEntity<City> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                City.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorId_entaoHttpStatus200() {
        Long id = cidadeEntityFactory().getId();
        ResponseEntity<City> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                City.class
        );
        City city = response.getBody();
        assertNotNull(city.getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorIdFalhar_entaoNoContent() {
        // Quando o ID nao existe na base de dados
        long id = - 1;
        ResponseEntity<City> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT +  "/" + id ),
                City.class
        );
        City city = response.getBody();
        assertNull(city);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarTodos_entaoStatus200 () {
        cidadeEntityFactory(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(response.getBody().size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200() {
        City city = cidadeEntityFactory();
        city.setCountry("YY");
        HttpEntity<City> entity = new HttpEntity<>(city);
        ResponseEntity<City> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                City.class
        );
        assertEquals(response.getBody().getCountry(), "YY");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        City city = new City();
        city.setId((long) - 1);
        HttpEntity<City> entity = new HttpEntity<>(city);
        ResponseEntity<City> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                City.class
        );
        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }
}