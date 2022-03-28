package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Image;
import dh.projetointegradorctd.backend.repository.ImageRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ImageControllerTest {

    private final String END_POINT = "images/";
    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ImageRepository imageRepository;

    @LocalServerPort
    private int serverPort;

    private Image getValidImage() {
        Image image = new Image();
        image.setTitle("imagem-test");
        image.setUrl("http://0.0.0.0");
        Set<ConstraintViolation<Image>> violacoes = validator.validate(image);
        assertThat(violacoes.size()).isZero();
        return image;
    }

    // Persiste e retorna uma entidade Image
    public Image imageEntityFactory() {
        return imageRepository.save(getValidImage());
    }

    @Test
    public void quandoCriar_entaoHttpStatus201() {
        HttpEntity<Image> entity = new HttpEntity<>(getValidImage());
        ResponseEntity<Image> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Image.class
        );
        Image image = response.getBody();
        assertNotNull(image.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void quandoCriarFalhar_entaoHttpStatus400 () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Image image = new Image();
        image.setId((long) 1);
        HttpEntity<Image> entity = new HttpEntity<>(image);
        ResponseEntity<Image> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Image.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorId_entaoHttpStatus200() {
        Long id = imageEntityFactory().getId();
        ResponseEntity<Image> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + id ),
                Image.class
        );
        Image image = response.getBody();
        assertNotNull(image.getId());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarPorIdFalhar_entaoNoContent() {
        // Quando o ID nao existe na base de dados
        long id = - 1;
        ResponseEntity<Image> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT +  "/" + id ),
                Image.class
        );
        Image image = response.getBody();
        assertNull(image);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    public void quandoBuscarTodos_entaoStatus200 () {
        imageEntityFactory(); // Garante que exista algum registro antes que o teste seja executado.
        var response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                List.class
        );
        assertTrue(response.getBody().size() > 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizar_entaoStatus200()  {
        Image image = imageEntityFactory();
        image.setTitle("atualizado-test");
        HttpEntity<Image> entity = new HttpEntity<>(image);
        ResponseEntity<Image> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Image.class
        );
        assertEquals(response.getBody().getTitle(), "atualizado-test");
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void quandoAtualizarFalhar_entaoNoContent () {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Image image = new Image();
        image.setId((long) - 1);
        HttpEntity<Image> entity = new HttpEntity<>(image);
        ResponseEntity<Image> response = this.testRestTemplate.exchange(
                getLocalUrl(this.serverPort, END_POINT),
                HttpMethod.PUT,
                entity,
                Image.class
        );
        assertNull(response.getBody());
        assertEquals(204, response.getStatusCodeValue());
    }
}
