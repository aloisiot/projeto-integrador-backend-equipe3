package dh.projetointegradorctd.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class DateRangeDto implements Serializable {
    private LocalDate startDate;
    private LocalDate endDate;
}
