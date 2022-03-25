package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.dataStorage.Category;
import dh.projetointegradorctd.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/categories", produces = "application/json;charset=UTF-8")
public class CategoryController extends TemplateCrudController<Category> {

    @Autowired
    public CategoryController(CategoryService service) {
        super(service);
    }
}
