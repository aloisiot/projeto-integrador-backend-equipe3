package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.model.storage.Image;
import dh.projetointegradorctd.backend.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService extends TemplateCrudService<Image> {

    @Autowired
    public ImageService(ImageRepository repository) {
        super(repository);
    }
}
