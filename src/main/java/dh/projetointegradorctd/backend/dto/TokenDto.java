package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.model.auth.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
public class TokenDto implements Serializable {

	@NotBlank
	private final String token;

	@NotBlank
	private final String type;

	@NotNull
	private final User userDetails;

	public TokenDto(String token, String type, User userDetails) {
		this.token = token;
		this.type = type;
		this.userDetails = userDetails;
	}
}
