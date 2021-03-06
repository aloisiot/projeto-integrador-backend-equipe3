package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.InvalidDateRangeException;
import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.service.BookingService;
import dh.projetointegradorctd.backend.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador de reservas
 */
@RestController
@RequestMapping(value = "/bookings", produces = "application/json;charset=UTF-8")
public class BookingController {

    /**
     * Instância do serviço de reservas
     */
    @Autowired
    private BookingService service;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    @Operation(summary = "Registrar nova entidade")
    public ResponseEntity<Booking> save (@RequestBody Booking booking) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(booking));
    }

    /**
     * Endpoint para a busca de reservas de um cliente.
     * @param token Token do usuário para a autenticação da transação conexão.
     * @return Lista de reservas pertencentes ao cliente
     */
    @GetMapping("/by-client")
    @Operation(summary = "Busca reservas de um cliente com base em seu seu ID")
    public ResponseEntity<List<Booking>> findAllByClientId(@RequestHeader (name="Authorization") String token) {
        long userId = tokenService.getUserIdFromToken(token.substring(7));
        return ResponseEntity.ok(service.findAllByClientId(userId));
    }

    /**
     * Endpoint para a busca de reservas relacionadas a um produto com base em seu ID.
     * @param productId ID do produto
     * @return Lista de reservas relacionadas ao produto
     */
    @GetMapping("/by-product/{productId}")
    @Operation(summary = "Busca reservas de um cliente com base em seu seu ID")
    public ResponseEntity<List<Booking>> findAllByProductId(@PathVariable Long productId) {
        return ResponseEntity.ok(service.findAllByProductId(productId));
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
