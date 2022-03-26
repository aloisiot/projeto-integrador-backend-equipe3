package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.model.storage.Reservation;
import dh.projetointegradorctd.backend.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

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
            throw new ResourceAccessException("Não encontrada nenhuma reserva para esse usuário");
        }
        return reservations;
    }
}
