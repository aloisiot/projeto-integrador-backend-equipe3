package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.storage.Evaluation;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.ClientRepository;
import dh.projetointegradorctd.backend.repository.EvaluationRepository;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.junit.jupiter.api.Test;
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

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Optional;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class EvaluationControllerTest {

    @Value("${api-base-path}/evaluations/")
    private String END_POINT;

    private static final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Evaluation validEvaluationFactory() {
        Long productId = productRepository.getMaxId();
        Product product = productRepository.findById(productId).orElse(null);

        Long clientId = clientRepository.getMaxId();
        Client client = clientRepository.findById(clientId).orElse(null);
        if(client == null) {
            client = new Client();
            client.setEmail("email@test.dev");
            client.setPassword("passwordtest");
            client.setName("client-test");
            client.setLastname("client-test");
            client = clientRepository.save(client);
        }

        Evaluation evaluation = new Evaluation();
        evaluation.setStars(5);
        evaluation.setComment("comment-test");
        evaluation.setProduct(product);
        evaluation.setClient(client);
        return evaluation;
    }

    private Evaluation evaluationEntityFactory(){
        return evaluationRepository.save(validEvaluationFactory());
    }

    @Test
    public void whenCreate_thenHttpStatus201() {
        Evaluation evaluation = validEvaluationFactory();
        HttpEntity<Evaluation> entity = new HttpEntity<>(evaluation);
        String url = getLocalUrl(this.serverPort, END_POINT);
        var response =
                this.testRestTemplate.postForEntity(url, entity, Evaluation.class);

        evaluation = response.getBody();
        assert evaluation != null : "Falha na requisição - retorno nulo";
        assertNotNull(evaluation.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenFindByUserId_thenStatus200() {
        Evaluation evaluation = validEvaluationFactory();
        String url = getLocalUrl(this.serverPort, END_POINT);
        var entity = new HttpEntity<>(evaluation);
        var response =
                this.testRestTemplate.postForEntity(url, entity, Evaluation.class);

        evaluation = response.getBody();
        assert evaluation != null : "Falha na requisição - retorno nulo";
        Long productId = evaluation.getProduct().getId();
        List<Evaluation> evaluations = evaluationRepository.findByProductId(productId);
        for(Evaluation e: evaluations) {
            assertEquals(evaluation.getProduct().getId(), evaluation.getProduct().getId());
        }
    }

    @Test
    public void whenCreateFails_thenHttpStatus422 ()  {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Evaluation evaluation = evaluationEntityFactory();
        String url = getLocalUrl(this.serverPort, END_POINT);
        var entity = new HttpEntity<>(evaluation);
        var response =
                this.testRestTemplate.postForEntity(url, entity,Evaluation.class);

        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void whernFindAllByProductId_thenStatus200() {
        Long id = evaluationEntityFactory().getProduct().getId();
        String url = getLocalUrl(this.serverPort, END_POINT + "by-product/" + id );
        var response = this.testRestTemplate.getForEntity( url, List.class);
        var product = response.getBody();
        assert product != null : "Falha na requisição - retorno nulo";
        assertNotEquals(product.size(), 0);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void whenDeleteById_thenStatus204() {
        long id = evaluationEntityFactory().getId();
        String url = getLocalUrl(this.serverPort, END_POINT + "/" + id);
        this.testRestTemplate.delete(url);
        Optional<Evaluation> evaluation = evaluationRepository.findById(id);
        assertTrue(evaluation.isEmpty());
    }
}