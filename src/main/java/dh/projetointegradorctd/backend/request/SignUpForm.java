package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignUpForm extends SignInForm {
    @NotEmpty(message = "O nome do usuário deve ser especificado")
    private String name;

    @NotEmpty(message = "O ultimo nome do usuário deve ser especificado")
    private String lastname;
}
