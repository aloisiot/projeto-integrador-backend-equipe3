package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.model.storage.Booking;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Serviço para a manipulação e busca de registros de produtos.
 */
@Service
public class ProductService extends TemplateCrudService<Product> {

    private final ProductRepository repository;

    /**
     * Instância do serviço de reservas auto injetada
     */
    @Autowired
    private BookingService bookingService;

    /**
     * Construtor padrão par
     * @param repository Instância de um repositorio cuja classe extenda Implemente ProductRepository
     */
    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = (ProductRepository) super.repository;
    }

    /**
     * Busca na base de dados todos os produtos com base no ID de uma categoria.
     * @param categoryId ID da categoria cujos produtos seram buscados.
     * @return Lista de peodutos relacionados à categoria especificada.
     * @throws ResorceNotFoundException Caso nenhum produto seja encontrado para tal categoria.
     */
    public List<Product> findAllByCategoryId(Long categoryId) throws ResorceNotFoundException {
        List<Product> products = repository.findAllByCategoryId(categoryId);
        if(products.isEmpty()) {
            throw new ResorceNotFoundException();
        }
        return products;
    }

    /**
     * Busca na base de dados todos os produtos com base no ID de uma cidade.
     * @param cityId ID da cidade cujos produtos seram buscados.
     * @return Lista de peodutos relacionados à cidade especificada.
     * @throws ResorceNotFoundException Caso nenhum produto seja encontrado para tal cidade.
     */
    public List<Product> findAllByCityId(Long cityId) throws ResorceNotFoundException {
        List<Product> products = repository.findAllByCityId(cityId);
        if(products.isEmpty()) {
            throw new ResorceNotFoundException();
        }
        return products;
    }

    /**
     * Busca produtos que não estejam reservados para um determinado intervalo de datas.
     * @param dateRange Intervalo de datas base para a busca.
     * @return Lista de produtos que não possuem reservas dentro do intervalo de datas especificado.
     */
    public List<Product> findByAvailableDateRange(DateRangeDto dateRange) {
        List<Booking> bookings;
        try{
            bookings = bookingService.findAllByDateRange(dateRange);
        } catch (ResorceNotFoundException e) {
            return findAll();
        }
        Set<Long> productsId = bookings.stream()
                .map(booking -> booking.getProduct().getId())
                .collect(Collectors.toSet());

        return repository.findAllByIdIsNotIn(productsId);
    }

    /**
     * Busca produtos que não estejam reservados para um determinado intervalo de datas e que estejam associados à determinada cidade.
     * @param cityId ID da cidade cujos produtos seram buscados.
     * @param dateRange Intervalo de datas base para a busca.
     * @return Lista de produtos que não possuem reservas dentro do intervalo de datas especificado e que estejam relacionados à cidade especificada.
     */
    public List<Product> findAllByCityAndAvailableDateRange(Long cityId, DateRangeDto dateRange) {
        return findByAvailableDateRange(dateRange)
                .stream()
                .filter(product -> product.getCity().getId().equals(cityId))
                .collect(Collectors.toList());
    }
}
