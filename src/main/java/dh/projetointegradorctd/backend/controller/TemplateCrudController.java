package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.DataBaseEntity;
import dh.projetointegradorctd.backend.service.TemplateCrudService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class TemplateCrudController<T extends DataBaseEntity> {

    protected TemplateCrudService<T> service;

    public TemplateCrudController(TemplateCrudService<T> service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Registrar nova entidade")
    public ResponseEntity<T> save (@RequestBody T entity) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar entidade com base no ID")
    public ResponseEntity<T> findById (@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping
    @Operation(summary = "Buscar todas as entidades")
    public ResponseEntity<List<T>> FindAll () {
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping
    @Operation(summary = "Atualizar entidade")
    public ResponseEntity<T> update (@RequestBody T entity) {
        return ResponseEntity.ok(service.update(entity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar entidade com base no ID")
    public ResponseEntity<?> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity
                .noContent()
                .header("message", "excluido com sucesso")
                .build();
    }
}
