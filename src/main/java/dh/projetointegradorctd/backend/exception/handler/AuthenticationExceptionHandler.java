package dh.projetointegradorctd.backend.exception.handler;

import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
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
    public ResponseEntity<?> unautorizedHandler(ForbiddenException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> duplicatedEmailErrorHandler(SQLIntegrityConstraintViolationException e) {
        return ResponseEntity
                .badRequest()
                .body("{\"error\": \"Email duplicado\"}");
    }
}
