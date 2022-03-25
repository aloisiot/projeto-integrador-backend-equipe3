package dh.projetointegradorctd.backend.exception.handler;

import dh.projetointegradorctd.backend.exception.global.EmpityRepositoryException;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ResorceNotFoundException.class, EmpityRepositoryException.class })
    public ResponseEntity<?> noContentHandler(Exception exception) {
        return ResponseEntity
                .noContent()
                .build();
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> unprocessableEntityHandler(UnprocessableEntityException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }
}
