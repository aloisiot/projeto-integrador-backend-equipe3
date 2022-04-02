package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class SignInForm {

	@NotEmpty(message = "O email deve ser especificado")
	@Email(message = "email invalido")
	private String email;

	@Size(min = 8, max = 255, message = "a senha deve tem no minimo oito caracters")
	@NotEmpty(message = "A senha deve ser especificada")
	private String password;
}
