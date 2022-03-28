package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p where p.city.id = :id")
    List<Product> findAllByCityId(@Param("id") Long id);

    @Query("select p from Product p where p.category.id = :id")
    List<Product> findAllByCategoryId(@Param("id") Long id);

    @Query("select count(p) from Product p where p.category.id = :categoryId")
    Integer countByCategoryId(@Param("categoryId") Long categoryId);

    @Query("select p from Product p where p.id not in :ids")
    List<Product> findAllByIdIsNotIn(@Param("ids") Set<Long> ids);
}