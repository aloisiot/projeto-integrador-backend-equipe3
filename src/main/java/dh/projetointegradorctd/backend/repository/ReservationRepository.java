package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r where r.client.id = :id")
    List<Reservation> findByClientId(@Param("id") Long id);
}