package dh.projetointegradorctd.backend.exception.global;

public class ParameterMisuseException extends UnprocessableEntityException {
    public ParameterMisuseException(String message) {
        super(message);
    }
}
