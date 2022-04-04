package dh.projetointegradorctd.backend.model.actor;

import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.ClientRepository;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientTest {

    private Client client;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        Long productId = productRepository.getMaxId();
        Product product = productRepository.findById(productId).orElse(null);
        Long clientId = clientRepository.getMaxId();
        this.client = clientRepository.findById(clientId).orElse(null);
        if(product != null && client != null) {
            this.client.setFavoriteProducts(List.of(product));
        }
    }

    @Test
    void getFavoriteProducts() {
        assertThat(this.client.getFavoriteProducts()).isNotNull();
    }

    @Test
    void getClientAuthorities() {
        client.getAuthorities().forEach(role ->
                assertThat(role.getAuthority()).isEqualTo("CLIENT"));
    }
}