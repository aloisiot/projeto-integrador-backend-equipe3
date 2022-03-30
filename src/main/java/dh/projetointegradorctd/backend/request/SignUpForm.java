package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Getter
@Setter
public class SignUpForm {
    @NotEmpty(message = "O nome do usuário deve ser especificado")
    private String name;

    @NotEmpty(message = "O ultimo nome do usuário deve ser especificado")
    private String lastname;

    @NotEmpty(message = "O email deve ser especificado")
    @Email(message = "email invalido")
    private String email;

    @NotEmpty(message = "A senha deve ser especificada")
    private String password;
}
