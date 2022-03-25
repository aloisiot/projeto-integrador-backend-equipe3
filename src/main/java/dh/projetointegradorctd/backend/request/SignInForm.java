package dh.projetointegradorctd.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SignInForm {

	@NotEmpty
	@Email(message = "email invalido")
	private String email;

	@NotEmpty
	private String password;

}
