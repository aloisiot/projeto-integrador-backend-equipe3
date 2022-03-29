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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class EvaluationControllerTest {
    private final String END_POINT = "evaluations/";

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
        Product product = productRepository.findById(productId).get();

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
        evaluation.setStarts(5);
        evaluation.setComment("comment-test");
        evaluation.setProduct(product);
        evaluation.setClient(client);
        return evaluation;
    }

    private Evaluation evaluationEntityFactory(){
        return evaluationRepository.save(validEvaluationFactory());
    }

    @Test
    public void whenCreate_status201() {
        Evaluation evaluation = validEvaluationFactory();
        HttpEntity<Evaluation> entity = new HttpEntity<>(evaluation);
        ResponseEntity<Evaluation> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Evaluation.class
        );
        evaluation = response.getBody();
        assertNotNull(evaluation.getId());
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    public void whenFindByUserId_status200() {
        Evaluation evaluation = validEvaluationFactory();

        HttpEntity<Evaluation> entity = new HttpEntity<>(evaluation);
        ResponseEntity<Evaluation> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Evaluation.class
        );
        evaluation = response.getBody();

        Long productId = evaluation.getProduct().getId();
        List<Evaluation> evaluations = evaluationRepository.findByProductId(productId);
        for(Evaluation e: evaluations) {
            assertEquals(evaluation.getProduct().getId(), evaluation.getProduct().getId());
        }
    }

    @Test
    public void whenCreateFailure_status422 ()  {
        // Quando solicitação post contem um id, entao UnprocessableEntityException
        Evaluation evaluation = evaluationEntityFactory();
        HttpEntity<Evaluation> entity = new HttpEntity<>(evaluation);
        ResponseEntity<Evaluation> response = this.testRestTemplate.postForEntity(
                getLocalUrl(this.serverPort, END_POINT),
                entity,
                Evaluation.class
        );
        assertEquals(422, response.getStatusCodeValue());
    }

    @Test
    public void whernFindByProductId_status200() {
        Long id = evaluationEntityFactory().getProduct().getId();
        ResponseEntity<List> response = this.testRestTemplate.getForEntity(
                getLocalUrl(this.serverPort, END_POINT + "by-product/" + id ),
                List.class
        );
        List<Evaluation> product = response.getBody();
        assertNotEquals(product.size(), 0);
        assertEquals(200, response.getStatusCodeValue());
    }
}