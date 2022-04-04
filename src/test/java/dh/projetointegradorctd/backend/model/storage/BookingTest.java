package dh.projetointegradorctd.backend.model.storage;

import dh.projetointegradorctd.backend.BackEndApplicationTests;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingTest {
    private Product product;
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

    void setUp() {
        BackEndApplicationTests.setUp(cityRepository, categoryRepository, productRepository, clientRepository, roleRepository);
        long productId = productRepository.getMaxId();
        this.product = productRepository.findById(productId).orElse(null);

        long clientId = clientRepository.getMaxId();
        this.client = clientRepository.findById(clientId).orElse(null);
    }

    @Test
    public void productAtributes() {
        setUp();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Booking booking = new Booking();
        Set<ConstraintViolation<Booking>> violations = validator.validate(booking);

        assertEquals(violations.size(), 5);

        LocalDate dateNow = LocalDate.now();
        LocalTime timeNow = LocalTime.now();

        booking.setProduct(this.product);
        booking.setClient(this.client);
        booking.setStartDate(dateNow);
        booking.setEndDate(dateNow);
        booking.setStartTime(timeNow);
        violations = validator.validate(booking);

        assertEquals(violations.size(), 0);
        assertEquals(dateNow, booking.getStartDate());
        assertEquals(dateNow, booking.getEndDate());
        assertEquals(timeNow, booking.getStartTime());

        bookingRepository.save(booking);
        assertNotNull(booking.getId());
        assertEquals(this.product.getId(), booking.getProduct().getId());
        assertEquals(this.client.getId(), booking.getClient().getId());
    }
}