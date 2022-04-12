package dh.projetointegradorctd.backend.model.actor;

import dh.projetointegradorctd.backend.BackEndApplicationTests;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ClientTest {

    private Client client;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setUp() {
        BackEndApplicationTests.setUp(cityRepository, categoryRepository, productRepository, clientRepository, roleRepository);
        Long productId = productRepository.getMaxId();
        Product product = productRepository.findById(productId).orElse(null);
        Long clientId = clientRepository.getMaxId();
        this.client = clientRepository.findById(clientId).orElse(null);
        if(product != null && client != null) {
            this.client.setFavoriteProducts(Set.of(product));
        }
    }

    @Test
    public void getFavoriteProducts() {
        assertThat(this.client.getFavoriteProducts()).isNotNull();
    }

    @Test
    public void getClientAuthorities() {
        client.getAuthorities().forEach(role ->
                assertThat(role.getAuthority()).isEqualTo("CLIENT"));
    }
}