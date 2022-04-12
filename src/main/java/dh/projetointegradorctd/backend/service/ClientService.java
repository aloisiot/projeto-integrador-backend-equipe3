package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.FavoriteDto;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.security.DuplicatedEmailException;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.ClientRepository;
import dh.projetointegradorctd.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ClientService extends TemplateCrudService<Client> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    public ClientService(ClientRepository repository) {
        super(repository);
    }

    @Override
    public Client save(Client client) throws Exception {
        if(userRepository.existsByEmail(client.getEmail())) {
            throw new DuplicatedEmailException();
        }
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        return super.save(client);
    }

    public Set<Product> findFavoritesByClientId(Long clientId) {
        Optional<Client> client = repository.findById(clientId);
        if(client.isEmpty() || client.get().getFavoriteProducts().isEmpty()){
            throw new ResorceNotFoundException();
        }
        return client.get().getFavoriteProducts();
    }

    public void handlerFavorites(FavoriteDto favoriteDto) {
        Client client = findById(favoriteDto.getClientId());
        Product product = productService.findById(favoriteDto.getProductId());
        Set<Product> products = client.getFavoriteProducts();
        if(products.contains(product)) {
            products.remove(product);
        } else {
            products.add(product);
        }
        client.setFavoriteProducts(products);
        repository.save(client);
    }

    public Boolean productIsFavorite(Long clientId, Long productId) {
        Client client = repository.findById(clientId).orElse(null);
        if(client != null) {
            return client.getFavoriteProducts()
                    .stream()
                    .anyMatch(product -> productId.equals(product.getId()));
        }
        return false;
    }
}
