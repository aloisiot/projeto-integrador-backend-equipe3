package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Evaluation;
import dh.projetointegradorctd.backend.service.EvaluationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/evaluations", produces = "application/json;charset=UTF-8")
public class EvaluationController extends TemplateCrudController<Evaluation> {

    private final EvaluationService service;

    public EvaluationController(EvaluationService service) {
        super(service);
        this.service = (EvaluationService) super.service;
    }

    @GetMapping("/by-product/{produtoId}")
    @Operation(summary = "Busca todas as avaliações de um produto com base no ID do produto")
    private ResponseEntity<List<Evaluation>> findAllByProductId(@PathVariable Long produtoId) {
        return ResponseEntity.ok(service.findAllByProductId(produtoId));
    }
}
