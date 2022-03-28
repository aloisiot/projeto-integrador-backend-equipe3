package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Reservation;
import dh.projetointegradorctd.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationService extends TemplateCrudService<Reservation> {

    private final ReservationRepository repository;

    @Autowired
    public ReservationService(ReservationRepository repository) {
        super(repository);
        this.repository = (ReservationRepository) super.repository;
    }

    public List<Reservation> findByClientId(Long clientId) {
        List<Reservation> reservations = repository.findByClientId(clientId);
        if(reservations.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse usuário");
        }
        return reservations;
    }

    public List<Reservation> findByDateRange(DateRangeDto dateRange) {
        LocalDate startDate = dateRange.getStartDate();
        LocalDate endDate = dateRange.getEndDate();
        List<Reservation> reservations = repository.findByDateRange(startDate, endDate);
        if(reservations.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse intervalo de datas");
        }
        return reservations;
    }
}
