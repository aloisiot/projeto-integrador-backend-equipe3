package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.FavoriteDto;
import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.service.ClientService;
import dh.projetointegradorctd.backend.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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

    @Autowired
    private TokenService tokenService;

    /**
     * Endopoint para a busca de todos os produtos favoritos de um cliente.
     * @param token Token de autenticação do cliente
     * @return Lista contendo os produtos favoritados pelo cliente em questão.
     */
    @GetMapping("/favorite-products")
    @Operation(summary = "Busca uma lista de produtos favoritos com base no ID do cliente")
    public ResponseEntity<Set<Product>> findFavoritesByClientId(@RequestHeader (name="Authorization") String token) {
        if(token != null) {
            long userId = tokenService.getUserIdFromToken(token.substring(7));
            return ResponseEntity.ok(service.findFavoritesByClientId(userId));
        }
        throw new ForbiddenException();
    }

    @PutMapping("/favorite-products")
    public ResponseEntity<?> handlerFavorites(@RequestBody FavoriteDto favoriteDto) {
        service.handlerFavorites(favoriteDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/product-is-favorite/{productId}")
    public ResponseEntity<Boolean> productIsFavorite(
            @PathVariable Long productId,
            @RequestHeader (name="Authorization") String token
    ) {
        if(token != null) {
            long userId = tokenService.getUserIdFromToken(token.substring(7));
            return ResponseEntity.ok(service.productIsFavorite(userId, productId));
        }
        throw new ForbiddenException();
    }
}
