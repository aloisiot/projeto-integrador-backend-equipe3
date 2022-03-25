package dh.projetointegradorctd.backend.response;

import dh.projetointegradorctd.backend.model.auth.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class TokenResponse {

	@NotBlank
	private final String token;

	@NotBlank
	private final String type;

	@NotNull
	private final User userDetails;

	public TokenResponse(String token, String type, User userDetails) {
		this.token = token;
		this.type = type;
		this.userDetails = userDetails;
	}
}
