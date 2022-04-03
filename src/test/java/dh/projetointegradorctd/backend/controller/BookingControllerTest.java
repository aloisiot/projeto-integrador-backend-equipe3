package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.BackEndApplicationTests;
import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.model.storage.Product;
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

import java.time.LocalTime;
import java.util.List;

import static dh.projetointegradorctd.backend.util.context.Url.getLocalUrl;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingControllerTest {

    @Value("${api-base-path}/bookings/")
    private String END_POINT;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private final String startDateStr = "2022-10-01";
    private final String endDateStr = "2022-10-11";

    @BeforeEach
    void setUp() throws InvalidDateRangeException {
        BackEndApplicationTests.setUp(
                cityRepository,
                categoryRepository,
                productRepository,
                clientRepository,
                roleRepository
        );
        DateRangeDto dateRange = DateRangeDto.instanceOf(startDateStr, endDateStr);
        Booking booking = new Booking();
        booking.setStartTime(LocalTime.now());
        booking.setStartDate(dateRange.getStartDate());
        booking.setEndDate(dateRange.getEndDate());
        long clientId = clientRepository.getMaxId();
        Client client = new Client();
        client.setId(clientId);
        booking.setClient(client);
        long productId = productRepository.getMaxId();
        Product product = new Product();
        product.setId(productId);
        booking.setProduct(product);
        assertNotNull(bookingRepository.save(booking).getId());
    }

    @Test
    void whenFindAllByDateRange_thenStatusOk() {
        String relativePath = END_POINT + "by-date-range/" + startDateStr + "/" + endDateStr;
        String url = getLocalUrl(this.serverPort, relativePath);
        var response = this.testRestTemplate.getForEntity(url, List.class);
        var bookings = response.getBody();
        assert bookings != null : "A lista de reservas não foi encontrada";
        assertTrue(bookings.size() > 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}