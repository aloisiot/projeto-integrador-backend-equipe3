package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.dataStorage.Characteristic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CharacteristicRepository extends JpaRepository<Characteristic, Long> {
}