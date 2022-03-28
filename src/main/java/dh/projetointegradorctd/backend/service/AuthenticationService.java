package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.security.UnauthorizedException;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.request.SignUpForm;
import dh.projetointegradorctd.backend.request.SignInForm;
import dh.projetointegradorctd.backend.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientService clientService;

    public TokenDto signIn (SignInForm form) throws UnauthorizedException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);

        User user;
        try {
            Long id = tokenService.getUserIdFromToken(token);
            user = clientService.findById(id);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }

        return new TokenDto(token, "Bearer", user);
    }

    public void signUp (SignUpForm form) {
        Client client = new Client();
        client.setEmail(form.getEmail());
        client.setPassword(form.getPassword());
        client.setName(form.getName());
        client.setLastname(form.getLastname());
        clientService.save(client);
    }
}
