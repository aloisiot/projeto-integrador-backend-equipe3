package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.dataStorage.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}