package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products", produces = "application/json;charset=UTF-8")
public class ProductController extends TemplateCrudController<Product> {

    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        super(service);
        this.service = (ProductService) super.service;
    }

    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Busca produtos com base em uma categoria")
    public ResponseEntity<List<Product>> findAllByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.findAllByCategoryId(categoryId));
    }

    @GetMapping("/by-city/{cityId}")
    @Operation(summary = "Busca produtos com base em uma cidade")
    public ResponseEntity<List<Product>> findAllByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(service.findAllByCityId(cityId));
    }

    @GetMapping("by-available-date-range")
    @Operation(summary = "Busca produtos com base em em intervalo de datas disponivel")
    public ResponseEntity<List<Product>> findByAvailableDateRange(@RequestBody DateRangeDto dateRange) {
        return ResponseEntity.ok(service.findByAvailableDateRange(dateRange));
    }

    @GetMapping("/by-city/{cityId}/and-available-date-range")
    @Operation(summary = "Busca produtos com base em em intervalo de datas disponivel e em uma cidade especifica")
    public ResponseEntity<List<Product>> findAllByCityAndAvailableDateRange(
            @PathVariable Long cityId, @RequestBody DateRangeDto dateRange
    ) {
        return ResponseEntity.ok(service.findAllByCityAndAvailableDateRange(cityId, dateRange));
    }
}
