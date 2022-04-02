package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import dh.projetointegradorctd.backend.exception.security.DuplicatedEmailException;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.ClientRepository;
import dh.projetointegradorctd.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService extends TemplateCrudService<Client> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

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

    public List<Product> findFavoritesByClientId(Long clientId) {
        Optional<Client> client = repository.findById(clientId);
        if(client.isEmpty() || client.get().getFavoriteProducts().isEmpty()){
            throw new ResorceNotFoundException();
        }
        return client.get().getFavoriteProducts();
    }
}
