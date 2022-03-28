package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
}