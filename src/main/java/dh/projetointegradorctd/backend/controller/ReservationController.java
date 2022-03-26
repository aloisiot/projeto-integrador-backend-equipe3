package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Reservation;
import dh.projetointegradorctd.backend.service.ReservationService;
import dh.projetointegradorctd.backend.service.TemplateCrudService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
