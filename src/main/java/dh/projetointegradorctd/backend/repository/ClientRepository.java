package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.actor.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select max(c.id) from Client c")
    Long getMaxId();
}