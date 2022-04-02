package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de reservas
 */
@RestController
@RequestMapping(value = "/bookings", produces = "application/json;charset=UTF-8")
public class BookingController extends TemplateCrudController<Booking> {

    /**
     * Instância do serviço de reservas
     */
    private final BookingService service;

    /**
     * Construtor padrâo para o controlador de reservas
     * @param service Instancia do serviço de reservas
     */
    @Autowired
    public BookingController(BookingService service) {
        super(service);
        this.service = (BookingService) super.service;
    }

    /**
     * Endpoint para a busca de reservas de um cliente com base em seu ID
     * @param clientId ID do cliente
     * @return Lista de reservas pertencentes ao cliente
     */
    @GetMapping("/by-client/{clientId}")
    @Operation(summary = "Busca reservas de um cliente com base em seu seu ID")
    public ResponseEntity<List<Booking>> findAllByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.findAllByClientId(clientId));
    }

    /**
     * Busca as reservas em um intervalo de datas específico
     * @param startDate Data inicial do intervalo
     * @param endDate Data final do intervalo
     * @return Lista de reservas
     */
    @GetMapping("by-date-range/{startDate}/{endDate}")
    @Operation(summary = "Busca reservas por intervalo de datas")
    public ResponseEntity<?> findAllByDateRange(
            @PathVariable String startDate, @PathVariable String endDate
    ) throws InvalidDateRangeException {
        List<Booking> bookings = service.findAllByDateRange(DateRangeDto.instanceOf(startDate, endDate));
        return ResponseEntity.ok(bookings);
    }
}
