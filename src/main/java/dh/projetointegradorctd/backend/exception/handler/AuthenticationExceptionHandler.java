package dh.projetointegradorctd.backend.exception.handler;

import dh.projetointegradorctd.backend.exception.security.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler({
            UnauthorizedException.class,
            UsernameNotFoundException.class,
            AuthenticationException.class
    })
    public ResponseEntity<?> unautorizedHandler(UnauthorizedException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }
}
