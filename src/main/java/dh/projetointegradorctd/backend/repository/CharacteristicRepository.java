package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
}