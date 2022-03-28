package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.model.storage.City;
import dh.projetointegradorctd.backend.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService extends TemplateCrudService<City>{

    @Autowired
    public CityService(CityRepository repository) {
        super(repository);
    }
}
