package dh.projetointegradorctd.backend.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/** Objeto para tranferência de dados de um intervalo de datas */
@Getter
@Setter
public class DateRangeDto implements Serializable {

    /** Data inicial do intervalo */
    @NotNull(message = "Data inicial do intervalo não deve ser nula")
    private LocalDate startDate;

    /** Data final do intervalo */
    @NotNull(message = "Data final do intervalo não deve ser nula")
    private LocalDate endDate;
}
