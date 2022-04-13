package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.BackEndApplicationTests;
import dh.projetointegradorctd.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientControllerTest {

    @Value("${api-base-path}/clients/")
    private String END_POINT;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository cityRepository;

    private long clientId;

    @BeforeEach
    void setUp() {
        BackEndApplicationTests.setUp(
                cityRepository,
                categoryRepository,
                productRepository,
                clientRepository,
                roleRepository
        );

        this.clientId = clientRepository.getMaxId();
    }

    @Test // TODO - implementar teste com o token
    void whenFindFavoritesBiClientId_thenStatusOk() {
//        String relativePath = END_POINT + "favorite-products/" + this.clientId;
//        String url = getLocalUrl(this.serverPort, relativePath);
//        var response = this.testRestTemplate.getForEntity(url, List.class);
//        var favoriteProducts = response.getBody();
//        assert favoriteProducts != null : "A lista de produtos favoritos não foi encontrada";
//        assertTrue(favoriteProducts.size() > 0);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
