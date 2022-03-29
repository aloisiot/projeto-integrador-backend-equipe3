package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeDtoTest {

    @Test
    void validateAtributes() {
        RuntimeException e1;
        e1 = assertThrows(UnprocessableEntityException.class, () ->
                DateRangeDto.instanceOf("2022-10-12", "2022-10-11"));

        assertEquals("A data inicial do intervalo deve ser anterior ou igual a data final",
                e1.getMessage());

        RuntimeException e2;
        e2 = assertThrows(UnprocessableEntityException.class, () ->
                DateRangeDto.instanceOf(null, null));

        assertEquals("As datas do intervalo nao deve ser nulas", e2.getMessage());

        String startDate = "2022-10-12";
        String endDate = "2022-10-13";

        DateRangeDto dateRange = DateRangeDto.instanceOf(startDate, endDate);
        assertEquals(LocalDate.parse(startDate), dateRange.getStartDate());
        assertEquals(LocalDate.parse(endDate), dateRange.getEndDate());
    }

}