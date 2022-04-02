package dh.projetointegradorctd.backend.exception.security;

public class DuplicatedEmailException extends Exception {
    public DuplicatedEmailException() {
        super("Ja existe um usuário com o email especificado");
    }
}
