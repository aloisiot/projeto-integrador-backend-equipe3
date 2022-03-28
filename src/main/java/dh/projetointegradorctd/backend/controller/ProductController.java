package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.service.ProductService;
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
    public ResponseEntity<List<Product>> findAllByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(service.findAllByCategoryId(categoryId));
    }

    @GetMapping("/by-city/{cityId}")
    public ResponseEntity<List<Product>> findAllByCity(@PathVariable Long cityId) {
        return ResponseEntity.ok(service.findAllByCityId(cityId));
    }

    @GetMapping("by-available-date-range")
    public ResponseEntity<List<Product>> findByAvailableDateRange(@RequestBody DateRangeDto dateRange) {
        return ResponseEntity.ok(service.findByAvailableDateRange(dateRange));
    }
}
