package dh.projetointegradorctd.backend.model.auth;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {
    private static Validator validator;

    @BeforeAll
    public static void setupValidatorInstance() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void authorityValidation() {
        Role role = new Role();
        role.setAuthority(Role.Authority.ADMIN);
        assertThat(role.getAuthority()).isEqualTo("ADMIN");
        assertThat(role.getAuthority()).isNotEqualTo("CLIENT");

        role.setAuthority(Role.Authority.CLIENT);
        assertThat(role.getAuthority()).isEqualTo("CLIENT");
        assertThat(role.getAuthority()).isNotEqualTo("ADMIN");

        role.setAuthority(null);
        Set<ConstraintViolation<Role>> violations = validator.validate(role);
        Stream<ConstraintViolation<Role>> authorityVionations = violations.stream().filter(
                violation -> violation.getMessage().equals("A autorização do perfil deve ser especificada"));

        assertThat(authorityVionations.toArray().length).isEqualTo(1);
    }

}