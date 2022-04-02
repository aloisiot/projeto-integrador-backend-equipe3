package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Characteristic;
import dh.projetointegradorctd.backend.repository.CharacteristicRepository;
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
public class CharacteristicControllerTest {

    @Value("${api-base-path}/characteristics/")
    private String END_POINT;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private CharacteristicRepository characteristicRepository;

    @LocalServerPort
    private int serverPort;

    private Characteristic getValidCaracteristica() {
        Characteristic characteristic = new Characteristic();
        characteristic.setName("characteristic-test");
        Set<ConstraintViolation<Characteristic>> violacoes = validator.validate(characteristic);
        assertThat(violacoes.size()).isZero();
        return characteristic;
    }

    // Persiste e retorna uma entidade Characteristic
    public Characteristic caracteristicaEntityFactory() {
        return characteristicRepository.save(getValidCaracteristica());
    }

    @Test
    public void whenCreate_thenHttpStatus201() {
        HttpEntity<Characteristic> entity = new HttpEntity<>(getValidCaracteristica());
        ResponseEntity<Characteristic> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Characteristic.class
        );
        Characteristic characteristic = response.getBody();
        assertNotNull(characteristic.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenCreateFails_whenHttpStatus422 () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Characteristic characteristic = new Characteristic();
        characteristic.setId((long) 1);
        HttpEntity<Characteristic> entity = new HttpEntity<>(characteristic);
        ResponseEntity<Characteristic> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Characteristic.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorId_entaoHttpStatus200() {
        Long id = caracteristicaEntityFactory().getId();
        ResponseEntity<Characteristic> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                Characteristic.class
        );
        Characteristic characteristic = response.getBody();
        assertNotNull(characteristic.getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorIdFalhar_entaoNoContent() {
        // Quando o ID nao existe na base de dados
        long id = - 1;
        ResponseEntity<Characteristic> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT +  "/" + id ),
                Characteristic.class
        );
        Characteristic characteristic = response.getBody();
        assertNull(characteristic);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarTodos_entaoStatus200 () {
        caracteristicaEntityFactory(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(response.getBody().size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200() {
        Characteristic characteristic = caracteristicaEntityFactory();
        characteristic.setName("atualizado-test");
        HttpEntity<Characteristic> entity = new HttpEntity<>(characteristic);
        ResponseEntity<Characteristic> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Characteristic.class
        );
        assertEquals(response.getBody().getName(), "atualizado-test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Characteristic characteristic = new Characteristic();
        characteristic.setId((long) - 1);
        HttpEntity<Characteristic> entity = new HttpEntity<>(characteristic);
        ResponseEntity<Characteristic> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Characteristic.class
        );
        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }
}