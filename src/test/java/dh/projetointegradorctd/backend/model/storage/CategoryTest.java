package dh.projetointegradorctd.backend.model.storage;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateAtributes() {
        Category category = new Category();
        Set<ConstraintViolation<Category>> violations = validator.validate(category);

        assertEquals(violations.size(), 3);

        category.setDescription(" ");
        category.setQualification(" ");
        category.setUrlImage(" ");
        category.setProductsQuantity(-1);

        violations = validator.validate(category);
        assertEquals(5, violations.size());

        category.setDescription("description-test");
        category.setQualification("qualification-test");
        category.setUrlImage("http://valid-url.com");
        category.setProductsQuantity(1);

        violations = validator.validate(category);
        assertEquals(0, violations.size());
    }

}