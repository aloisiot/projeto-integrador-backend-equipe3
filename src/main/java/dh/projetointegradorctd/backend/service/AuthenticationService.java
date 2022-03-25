package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.security.UnauthorizedException;
import dh.projetointegradorctd.backend.model.auth.Authority;
import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.request.SignUpForm;
import dh.projetointegradorctd.backend.request.SignInForm;
import dh.projetointegradorctd.backend.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    public TokenResponse signIn (SignInForm form) throws UnauthorizedException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);

        User user;
        try {
            Long id = tokenService.getUserIdFromToken(token);
            user = userService.findById(id);
        } catch (Exception e) {
            throw new UnauthorizedException();
        }

        return new TokenResponse(token, "Bearer", user);
    }

    public void signUp (SignUpForm form) {
        User user = new User();
        user.setEmail(form.getEmail());
        user.setPassword(form.getPassword());
        user.setName(form.getName());
        user.setLastname(form.getLastname());

        Role role = new Role();
        role.setAuthority(Authority.CLIENT);
        user.setAuthorities(Set.of(role));

        userService.save(user);
    }
}
