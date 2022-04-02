package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.repository.RoleRepository;
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
class RoleControllerTest {

    @Value("${api-base-path}/roles/")
    private String END_POINT;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int serverPort;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        Role role = new Role();
        role.setAuthority(Role.Authority.ADMIN);
        roleRepository.save(role);

        role = new Role();
        role.setAuthority(Role.Authority.CLIENT);
        roleRepository.save(role);
    }

    @Test
    void whenFindAll_ThenHttpStatus200() {
        String url = getLocalUrl(this.serverPort, END_POINT);
        var response =
                testRestTemplate.getForEntity(url, List.class);

        var roles = response.getBody();
        assert roles != null : "Falha na requisição - retorno nulo";
        assertTrue(roles.size() > 0);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}