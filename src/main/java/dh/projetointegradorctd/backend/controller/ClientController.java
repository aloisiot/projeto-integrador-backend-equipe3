package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/clients", produces = "application/json;charset=UTF-8")
public class ClientController {

    @Autowired
    private ClientService service;

    @GetMapping("/favorites/{clientId}")
    @Operation(summary = "Busca uma lista de produtos favoritos com base no ID do cliente")
    private ResponseEntity<List<Product>> findFavoritesByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.findFavoritesByClientId(clientId));
    }
}
