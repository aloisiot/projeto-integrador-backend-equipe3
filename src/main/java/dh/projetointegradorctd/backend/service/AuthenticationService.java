package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.InternalServerError;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.security.AccountNotVerifiedException;
import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import dh.projetointegradorctd.backend.model.actor.Client;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.request.SignUpForm;
import dh.projetointegradorctd.backend.request.SignInForm;
import dh.projetointegradorctd.backend.dto.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    @Value("${FRONTEND_ORIGIN}")
    private String ORIGIN;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private EmailService emailService;

    public TokenDto signIn (SignInForm form) throws ForbiddenException {
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());

        Authentication authentication = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenService.generateToken(authentication);

        User user;
        try {
            Long id = tokenService.getUserIdFromToken(token);
            user = clientService.findById(id);
            if(!user.getIsEmailChecked()) {
                sendVerificationEmail(user);
                throw new AccountNotVerifiedException();
            }
        } catch (ResorceNotFoundException e) {
            throw new ForbiddenException();
        }

        return new TokenDto(token, "Bearer", user);
    }

    public void signUp (SignUpForm form) throws Exception {
        Client client = new Client();
        client.setEmail(form.getEmail());
        client.setPassword(form.getPassword());
        client.setName(form.getName());
        client.setLastname(form.getLastname());
        client = clientService.save(client);

        try {
            sendVerificationEmail(client);
        } catch (Exception e) {
            e.printStackTrace();
            if(client.getId() != null) {
                clientService.deleteById(client.getId());
            }
            throw new InternalServerError();
        }
    }

    private void sendVerificationEmail(User user) {
        String url = ORIGIN + "/validate-email/" + user.getId() + "/" + user.getEmail().hashCode();
        String message = "Clique no link para verificar seu email:\n" + url;
        emailService.send(
                message,
                "Digital Booking [ Verificação de email ]",
                user.getEmail()
        );
    }

    public Boolean validateEmail(long userId, Integer emailHash) {
        try {
            Client client = clientService.findById(userId);
            boolean isValid = emailHash.equals(client.getEmail().hashCode());
            if(isValid) {
                client.setIsEmailChecked(true);
                clientService.update(client);
            }
            return isValid;
        } catch (Exception e) {
            return false;
        }
    }
}
