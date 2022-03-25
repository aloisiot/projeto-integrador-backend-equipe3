package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.dataStorage.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
}