package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> usuario = userRepository.findByEmail(email);
		if (usuario.isEmpty()) {
			throw new UsernameNotFoundException("credenciais invalidas");
		}
		return usuario.orElse(null);
	}
}
