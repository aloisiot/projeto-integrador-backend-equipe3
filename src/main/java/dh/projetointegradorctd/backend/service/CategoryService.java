package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.EmpityRepositoryException;
import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Category;
import dh.projetointegradorctd.backend.repository.CategoryRepository;
import dh.projetointegradorctd.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService extends TemplateCrudService<Category> {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        super(repository);
    }

    @Override
    public Category findById(Long categoryId) {
        Category category = super.findById(categoryId);
        category.setProductsQuantity(productRepository.countByCategoryId(categoryId));
        return category;
    }

    @Override
    public List<Category> findAll() throws EmpityRepositoryException {
        List<Category> categories = super.findAll();
        for(Category c: categories) {
            Integer qtdProducts = productRepository.countByCategoryId(c.getId());
            c.setProductsQuantity(qtdProducts);
        }
        return categories;
    }

    @Override
    public Category update(Category category) throws ResorceNotFoundException {
        Category updatedCategory = super.update(category);
        Integer productsQuantity = productRepository.countByCategoryId(updatedCategory.getId());
        updatedCategory.setProductsQuantity(productsQuantity);
        return updatedCategory;
    }
}
