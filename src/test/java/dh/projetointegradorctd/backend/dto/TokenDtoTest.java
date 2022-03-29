package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.model.auth.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TokenDtoTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void validateAtributes(){
        String tokenStr = "asda-adasc-asd";
        String tokenType = "Bearer";

        TokenDto token = new TokenDto(null, null, null);
        Set<ConstraintViolation<TokenDto>> violations = validator.validate(token);
        assertEquals(3, violations.size());

        token = new TokenDto(" ", " ", new User());
        violations = validator.validate(token);
        assertEquals(2, violations.size());

        token = new TokenDto(tokenStr, tokenType, new User());
        violations = validator.validate(token);
        assertEquals(0, violations.size());
        assertEquals(token.getToken(), tokenStr);
        assertEquals(token.getType(), tokenType);
    }

}