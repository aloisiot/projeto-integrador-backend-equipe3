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

    /** Construtor padrão */
    public DateRangeDto(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Método fabrica de objetos DateRageDto
     * @param startDate Data inicial do intervalo mo formato yyyy-MM-dd
     * @param endDate Data final do intervalo mo formato yyyy-MM-dd
     * @return Instância de DateRangeDto com base nas datas passadas como parâmetro
     */
    public static DateRangeDto instanceOf(String startDate, String endDate) {
        return new DateRangeDto(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
