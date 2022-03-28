package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.model.storage.Reservation;
import dh.projetointegradorctd.backend.service.ReservationService;
import dh.projetointegradorctd.backend.service.TemplateCrudService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/reservations", produces = "application/json;charset=UTF-8")
public class ReservationController extends TemplateCrudController<Reservation> {

    private final ReservationService service;

    @Autowired
    public ReservationController(TemplateCrudService<Reservation> service) {
        super(service);
        this.service = (ReservationService) super.service;
    }

    @GetMapping("/by-client/{clientId}")
    @Operation(summary = "Busca reservas com base no ID de um cliente")
    public ResponseEntity<List<Reservation>> findByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.findByClientId(clientId));
    }

    @GetMapping("by-date-range")
    @Operation(summary = "Busca reservas por intervalo de datas")
    public ResponseEntity<?> findByDateRange(@RequestBody DateRangeDto dateRange) {
        List<Reservation> reservations = service.findByDateRange(dateRange);
        return ResponseEntity.ok(reservations);
    }
}
