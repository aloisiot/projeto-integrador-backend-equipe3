package dh.projetointegradorctd.backend.repository;

import dh.projetointegradorctd.backend.model.storage.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("select r from Booking r where r.client.id = :id")
    List<Booking> findAllByClientId(@Param("id") Long id);

    @Query("select r from Booking r " +
            "where" +
                "(:startDate between r.startDate and r.endDate)" +
            "or" +
                "(:endDate between r.startDate and r.endDate)" +
            "or" +
                "(:startDate <= r.startDate and :endDate >= r.endDate)")
    List<Booking> findAllByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
