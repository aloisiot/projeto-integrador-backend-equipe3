package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.dataStorage.Characteristic;
import dh.projetointegradorctd.backend.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/characteristics", produces = "application/json;charset=UTF-8")
public class CharacteristicController extends TemplateCrudController<Characteristic> {

    @Autowired
    public CharacteristicController(CharacteristicService service) {
        super(service);
    }
}
