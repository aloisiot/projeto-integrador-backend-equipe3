package dh.projetointegradorctd.backend.exception.global;

public class EmpityRepositoryException extends RuntimeException {
    public EmpityRepositoryException() {
        super("repositorio vazio");
    }

    public EmpityRepositoryException(String message) {
        super(message);
    }
}
