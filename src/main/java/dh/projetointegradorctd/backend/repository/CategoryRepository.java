package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.dataStorage.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{
}
