package dh.projetointegradorctd.backend.exception.handler;

import dh.projetointegradorctd.backend.exception.security.DuplicatedEmailException;
import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import org.hibernate.procedure.ParameterMisuseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class AuthenticationExceptionHandler {

    @ExceptionHandler({
            ForbiddenException.class,
            UsernameNotFoundException.class,
            AuthenticationException.class
    })
    public ResponseEntity<String> unautorizedHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }

    @ExceptionHandler(DuplicatedEmailException.class)
    public ResponseEntity<String> duplicatedEmailErrorHandler(Exception exception) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\": \"" + exception.getMessage() + "\"}");
    }

    @ExceptionHandler(ParameterMisuseException.class)
    public ResponseEntity<String> paramiterMisuseErrorHandler(Exception exception) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }
}
