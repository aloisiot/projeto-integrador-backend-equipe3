package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.FavoriteDto;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para o serviço de clientes.
 * Extende a classe TemplateCrudControler, por tanto herda todos os metodos CRUD convencionais.
 */
@RestController
@RequestMapping(value = "/clients", produces = "application/json;charset=UTF-8")
public class ClientController {

    /**
     * Instância autoinjetada do serviço de clientes.
     */
    @Autowired
    private ClientService service;

    /**
     * Endopoint para a busca de todos os produtos favoritos de um cliente com base no seu ID.
     * @param clientId ID do cliente cujos produtos seram buscados.
     * @return Lista contendo os produtos favoritados pelo cliente em questão.
     */
    @GetMapping("/favorite-products/{clientId}")
    @Operation(summary = "Busca uma lista de produtos favoritos com base no ID do cliente")
    private ResponseEntity<List<Product>> findFavoritesByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.findFavoritesByClientId(clientId));
    }

    @PostMapping("/favorite-products")
    public ResponseEntity<?> addFavorite(@RequestBody FavoriteDto favoriteDto) {
        service.addFavorite(favoriteDto);
        return ResponseEntity.ok().build();
    }
}
