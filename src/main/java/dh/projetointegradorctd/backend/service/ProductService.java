package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.dto.DateRangeDto;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.model.storage.Reservation;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ProductService extends TemplateCrudService<Product> {

    private final ProductRepository repository;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    public ProductService(ProductRepository repository) {
        super(repository);
        this.repository = (ProductRepository) super.repository;
    }

    public List<Product> findAllByCategoryId(Long categoryId) throws ResorceNotFoundException {
        List<Product> products = repository.findAllByCategoryId(categoryId);
        if(products.isEmpty()) {
            throw new ResorceNotFoundException();
        }
        return products;
    }

    public List<Product> findAllByCityId(Long cityId) throws ResorceNotFoundException {
        List<Product> products = repository.findAllByCityId(cityId);
        if(products.isEmpty()) {
            throw new ResorceNotFoundException();
        }
        return products;
    }

    public List<Product> findByAvailableDateRange(DateRangeDto dateRange) {
        List<Reservation> reservations;
        try{
            reservations = reservationService.findByDateRange(dateRange);
        } catch (ResorceNotFoundException e) {
            return findAll();
        }
        Set<Long> productsId = new HashSet<>();
        for (Reservation r: reservations) {
            productsId.add(r.getProduct().getId());
        }
        return repository.findAllByIdIsNotIn(productsId);
    }
}
