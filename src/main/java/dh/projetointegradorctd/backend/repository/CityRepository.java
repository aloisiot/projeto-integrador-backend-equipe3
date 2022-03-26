package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
}