package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Product;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService extends TemplateCrudService<Product> {

    private final ProductRepository repository;

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
}
