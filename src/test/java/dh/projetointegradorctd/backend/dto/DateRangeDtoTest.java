package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DateRangeDtoTest {

    @Test
    void validateAtributes() throws InvalidDateRangeException {
        String invalidDateRangeMessage = InvalidDateRangeException.defaultMessage;
        String nullDatesError = "As datas do intervalo nao deve ser nulas";
        Exception e1;
        e1 = assertThrows(InvalidDateRangeException.class, () ->
                DateRangeDto.instanceOf("2022-10-12", "2022-10-11"));

        assertEquals(invalidDateRangeMessage, e1.getMessage());

        Exception e2;
        e2 = assertThrows(UnprocessableEntityException.class, () ->
                DateRangeDto.instanceOf(null, null));

        assertEquals(nullDatesError, e2.getMessage());

        String startDate = "2022-10-12";
        String endDate = "2022-10-13";

        DateRangeDto dateRange = DateRangeDto.instanceOf(startDate, endDate);
        assertEquals(LocalDate.parse(startDate), dateRange.getStartDate());
        assertEquals(LocalDate.parse(endDate), dateRange.getEndDate());
    }

}