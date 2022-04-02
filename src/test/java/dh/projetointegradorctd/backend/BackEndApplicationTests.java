package dh.projetointegradorctd.backend;

import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.model.storage.Category;
import dh.projetointegradorctd.backend.model.storage.City;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BackEndApplicationTests {

	@Test
	public void contextLoad(){}

	public static void setUp(
			CityRepository cityRepository,
			CategoryRepository categoryRepository,
			ProductRepository productRepository,
			ClientRepository clientRepository,
			RoleRepository roleRepository
	) {
		Client client = new Client();
		client.setName("client-test");
		client.setLastname("test");
		client.setEmail("client." + System.currentTimeMillis() + "@mail.dev");
		client.setPassword("923u49d0aa-s0do213o");

		Role role = new Role();
		role.setAuthority(Role.Authority.ADMIN);
		roleRepository.save(role);

		role = new Role();
		role.setAuthority(Role.Authority.CLIENT);
		role = roleRepository.save(role);
		client.setAuthorities(Set.of(role));

		Product product = new Product();
		product.setName("product-test");
		product.setDescription("product-test");
		product.setLatitude("9832469834928");
		product.setLatitude("9832469834928");

		City city = new City();
		city.setName("city-test");
		city.setCountry("BR");
		cityRepository.save(city);
		product.setCity(city);

		Category category = new Category();
		category.setDescription("test-test-test");
		category.setQualification("category-test");
		category.setUrlImage("http://test.dev");
		category = categoryRepository.save(category);
		product.setCategory(category);

		product = productRepository.save(product);

		client.setFavoriteProducts(List.of(product));
		clientRepository.save(client);
	}

}
