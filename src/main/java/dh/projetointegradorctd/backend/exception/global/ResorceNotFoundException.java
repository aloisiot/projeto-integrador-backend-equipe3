package dh.projetointegradorctd.backend.exception.global;

public class ResorceNotFoundException extends RuntimeException {
    public ResorceNotFoundException() {
        super("recurso nao encontrado");
    }

    public ResorceNotFoundException(String message) {
        super(message);
    }
}
