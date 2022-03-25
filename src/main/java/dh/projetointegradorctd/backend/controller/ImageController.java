package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.model.dataStorage.Image;
import dh.projetointegradorctd.backend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/images", produces = "application/json;charset=UTF-8")
public class ImageController extends TemplateCrudController <Image> {

    @Autowired
    public ImageController(ImageService service) {
        super(service);
    }
}
