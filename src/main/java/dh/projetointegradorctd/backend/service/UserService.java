package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService extends TemplateCrudService <User> {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        super(repository);
    }

    @Override
    public User save(User user) throws UnprocessableEntityException {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return super.save(user);
    }
}
