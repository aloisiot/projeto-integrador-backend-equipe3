package dh.projetointegradorctd.backend.exception.security;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException() {
        super("Nao autorizado");
    }
}
