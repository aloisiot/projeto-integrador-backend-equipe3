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

@Service
public class ProductService extends TemplateCrudService<Product> {

    private final ProductRepository repository;

    @Autowired
    private BookingService bookingService;

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
        List<Booking> bookings;
        try{
            bookings = bookingService.findAllByDateRange(dateRange);
        } catch (ResorceNotFoundException e) {
            return findAll();
        }
        Set<Long> productsId = bookings.stream()
                .map(Booking::getId)
                .collect(Collectors.toSet());

        return repository.findAllByIdIsNotIn(productsId);
    }
    public List<Product> findAllByCityAndAvailableDateRange(Long cityId, DateRangeDto dateRange) {
        return findByAvailableDateRange(dateRange)
                .stream()
                .filter(product -> product.getCity().getId().equals(cityId))
                .collect(Collectors.toList());
    }
}
