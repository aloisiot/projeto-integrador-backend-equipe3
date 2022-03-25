package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.model.dataStorage.Characteristic;
import dh.projetointegradorctd.backend.repository.CharacteristicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CharacteristicService extends TemplateCrudService<Characteristic>{

    @Autowired
    public CharacteristicService(CharacteristicRepository repository) {
        super(repository);
    }
}
