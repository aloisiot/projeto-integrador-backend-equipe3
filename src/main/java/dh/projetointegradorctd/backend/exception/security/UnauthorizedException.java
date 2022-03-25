package dh.projetointegradorctd.backend.exception.security;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("Nao autorizado");
    }
}
