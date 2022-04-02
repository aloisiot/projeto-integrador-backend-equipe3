package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 *  Classe para tranferência de dados de um intervalo de datas.
 * Um intervalo só é válido caso a data inicial seja menor ou igual à data final
 * */
@Getter
public class DateRangeDto implements Serializable {

    /** Data inicial do intervalo */
    @NotNull(message = "Data inicial do intervalo não deve ser nula")
    private final LocalDate startDate;

    /** Data final do intervalo */
    @NotNull(message = "Data final do intervalo não deve ser nula")
    private final LocalDate endDate;

    /**
     * Construtor padrão
     * @param startDate Data inicial do intervalo. Deve ser menor ou igual à data final.
     * @param endDate Data final do intervalo.
     */
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
    public static DateRangeDto instanceOf(String startDate, String endDate) throws InvalidDateRangeException {
        if(startDate == null || endDate == null) {
            throw new UnprocessableEntityException("As datas do intervalo nao deve ser nulas");
        }
        LocalDate sDate = LocalDate.parse(startDate);
        LocalDate eDate = LocalDate.parse(endDate);
        if(sDate.isAfter(eDate)) {
            throw new InvalidDateRangeException();
        }
        return new DateRangeDto(LocalDate.parse(startDate), LocalDate.parse(endDate));
    }
}
