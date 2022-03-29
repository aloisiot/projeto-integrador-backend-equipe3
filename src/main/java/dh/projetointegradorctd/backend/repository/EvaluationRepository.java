package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Evaluation;
import dh.projetointegradorctd.backend.model.storage.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EvaluationRepository extends JpaRepository<Evaluation, Long> {
    @Query("select e from Evaluation e where e.product.id = :productId")
    List<Evaluation> findByProductId(@Param("productId") Long productId);
}