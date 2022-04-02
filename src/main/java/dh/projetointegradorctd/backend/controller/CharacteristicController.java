package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.storage.Characteristic;
import dh.projetointegradorctd.backend.service.CharacteristicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para o serviço de manipilação e busca de caracteristicas dos produtos
 */
@RestController
@RequestMapping(value = "/characteristics", produces = "application/json;charset=UTF-8")
public class CharacteristicController extends TemplateCrudController<Characteristic> {

    /**
     * Contrutor padrâo
     * @param service Instância do serviço de manipilação e busca de caracteristicas
     */
    @Autowired
    public CharacteristicController(CharacteristicService service) {
        super(service);
    }
}
