package dh.projetointegradorctd.backend.exception.global;

public class UnprocessableEntityException extends RuntimeException {
    public UnprocessableEntityException() {
        super("entidade improcessavel - verifique os dados enviados");
    }

    public UnprocessableEntityException(String message) {
        super(message);
    }
}
