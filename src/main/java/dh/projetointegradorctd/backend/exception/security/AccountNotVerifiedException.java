package dh.projetointegradorctd.backend.exception.security;

public class AccountNotVerifiedException extends RuntimeException {
    public AccountNotVerifiedException() {
        super("account not ferified");
    }
}
