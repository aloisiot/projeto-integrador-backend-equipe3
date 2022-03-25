package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.dataStorage.City;
import dh.projetointegradorctd.backend.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/cities", produces = "application/json;charset=UTF-8")
public class CityController extends TemplateCrudController <City> {

    @Autowired
    public CityController(CityService service) {
        super(service);
    }
}
