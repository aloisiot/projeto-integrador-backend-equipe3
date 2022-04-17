package dh.projetointegradorctd.backend.exception.global;

public class InternalServerError extends RuntimeException {
    public InternalServerError() {
        super("internal server error");
    }
}
