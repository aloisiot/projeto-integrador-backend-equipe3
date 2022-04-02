package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("select max(c.id) from City c")
    Long getMaxId();
}