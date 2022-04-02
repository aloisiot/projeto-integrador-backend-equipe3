package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador RSET do serviço de perfis do usuário.
 * Produz respostas application/json;charset=UTF-8.
 */
@RestController
@RequestMapping(value = "/roles", produces = "application/json;charset=UTF-8")
public class RoleController {

    /** Instância do serviço de perfis do usuãrio */
    @Autowired
    private RoleService service;

    /**
     * Lista todos os perfis de usuário do sistema
     * @return Entidade http contendo a lista de perfis - status 200
     */
    @GetMapping
    @Operation(summary = "Lista todos os perfis de usuário do sistema")
    public ResponseEntity<List<Role>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }
}
