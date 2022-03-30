package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignInForm {

	@NotEmpty(message = "O email deve ser especificado")
	@Email(message = "email invalido")
	private String email;

	@NotEmpty(message = "A senha deve ser especificada")
	private String password;

}
