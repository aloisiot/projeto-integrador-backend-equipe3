package dh.projetointegradorctd.backend.exception.handler;

import dh.projetointegradorctd.backend.exception.global.EmpityRepositoryException;
import dh.projetointegradorctd.backend.exception.global.InternalServerError;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ValidationException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ ResorceNotFoundException.class, EmpityRepositoryException.class })
    public ResponseEntity<?> noContentHandler(Exception exception) {
        return ResponseEntity
                .noContent()
                .build();
    }

    @ExceptionHandler({ UnprocessableEntityException.class })
    public ResponseEntity<String> unprocessableEntityHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }

    @ExceptionHandler({ ValidationException.class, MethodArgumentNotValidException.class })
    public ResponseEntity<String> validationExceptionHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }

    @ExceptionHandler({ InternalServerError.class })
    public ResponseEntity<String> internalServerErrorHandler(Exception exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"error\" : \"" + exception.getMessage() + "\"}");
    }
}
