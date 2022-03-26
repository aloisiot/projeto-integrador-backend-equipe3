package dh.projetointegradorctd.backend.model.dataStorage;

import dh.projetointegradorctd.backend.model.auth.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Reservation extends DataBaseEntity {

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
    @NotNull(message = "A reserva deve conter um usuário associado")
    private User user;

    @ManyToOne
    @NotNull(message = "A reserva deve conter um produto associado")
    private Product product;

}
