package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService extends TemplateCrudService<Booking> {

    private final BookingRepository repository;

    @Autowired
    public BookingService(BookingRepository repository) {
        super(repository);
        this.repository = (BookingRepository) super.repository;
    }

    public List<Booking> findAllByClientId(Long clientId) {
        List<Booking> bookings = repository.findAllByClientId(clientId);
        if(bookings.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse usuário");
        }
        return bookings;
    }

    public List<Booking> findAllByDateRange(DateRangeDto dateRange) {
        List<Booking> bookings = repository.findAllByDateRange(dateRange.getStartDate(), dateRange.getEndDate());
        if(bookings.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse intervalo de datas");
        }
        return bookings;
    }
}
