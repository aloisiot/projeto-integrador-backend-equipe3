package dh.projetointegradorctd.backend.model.storage;

import com.fasterxml.jackson.annotation.JsonProperty;
import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.model.actor.Client;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Table(name = "tb_bookings")
public class Booking extends DataBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A reserva deve conter uma hora inicial")
    private LocalTime startTime;

    @NotNull(message = "A reserva deve conter uma data inicial")
    private LocalDate startDate;

    @NotNull(message = "A reserva deve conter uma data final")
    private LocalDate endDate;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "A reserva deve conter um cliente associado")
    private Client client;

    @ManyToOne
    @NotNull(message = "A reserva deve conter um produto associado")
    private Product product;

    @PrePersist
    @PreUpdate
    private void checkDateRange() throws InvalidDateRangeException {
        if(this.startDate.isAfter(this.endDate)) {
            throw new InvalidDateRangeException();
        }
    }

    public int compareByStartDate(Booking other) {
        return this.getStartDate().isAfter(other.getStartDate()) ? 1 : -1;
    }
}
