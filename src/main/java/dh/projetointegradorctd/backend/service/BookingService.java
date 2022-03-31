package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.ParameterMisuseException;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.exception.global.UnprocessableEntityException;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService extends TemplateCrudService<Booking> {

    private final BookingRepository repository;

    @Autowired
    public BookingService(BookingRepository repository) {
        super(repository);
        this.repository = (BookingRepository) super.repository;
    }

    /**
     * Salva uma nova reserva caso não exista nenhuma reserva no mesmo intervalo de datas.
     * @param booking Entidade reserva a ser salva.
     * @return Retorna a reserva salva.
     * @throws UnprocessableEntityException Caso exista uma reserva no mesmo intervalo de datas.
     */
    @Override
    public Booking save(Booking booking) throws ParameterMisuseException {
        DateRangeDto dateRange = new DateRangeDto(booking.getStartDate(), booking.getEndDate());
        if(findAllByDateRange(dateRange).size() > 0) {
            throw new ParameterMisuseException("Ja existe uma reserva para esse intervalo de datas");
        }
        return super.save(booking);
    }

    /**
     * Busca o registros de reservas com base no id do cliente.
     * @param clientId ID do cliente cujas reservas pertencem .
     * @return Lista de reservas relacionadas ao cliente especificado.
     * @throws ResorceNotFoundException - Caso nenhum registro seja encontrado
     */
    public List<Booking> findAllByClientId(Long clientId) throws ResorceNotFoundException {
        List<Booking> bookings = repository.findAllByClientId(clientId);
        if(bookings.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse usuário");
        }
        return bookings.stream()
                .sorted(Booking::compareByStartDate)
                .collect(Collectors.toList());
    }

    /**
     * Busca todas as reservas cujas datas inicial e final estão dentro de um intervalo de datas.
     * @param dateRange Intervalo de datas a ser base para a busca.
     * @return Lista de reservas dentro do intervalo de datas especificado.
     * @throws ResorceNotFoundException - Caso nenhum registro seja encontrado
     */
    public List<Booking> findAllByDateRange(DateRangeDto dateRange) throws ResorceNotFoundException {
        List<Booking> bookings = repository.findAllByDateRange(dateRange.getStartDate(), dateRange.getEndDate());
        if(bookings.isEmpty()) {
            throw new ResorceNotFoundException("Não encontrada nenhuma reserva para esse intervalo de datas");
        }
        return bookings.stream()
                .sorted(Booking::compareByStartDate)
                .collect(Collectors.toList());
    }
}
