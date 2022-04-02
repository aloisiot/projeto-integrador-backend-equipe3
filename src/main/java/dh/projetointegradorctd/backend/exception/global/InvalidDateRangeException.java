package dh.projetointegradorctd.backend.exception.global;

public class InvalidDateRangeException extends Exception {

    public static final String defaultMessage =
            "Intervalo de datas invalido. A data inicial não deve ser maior que a data final";

    public InvalidDateRangeException() {
        super(defaultMessage);
    }
}
