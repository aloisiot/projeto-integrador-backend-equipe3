package dh.projetointegradorctd.backend.dto;

import dh.projetointegradorctd.backend.model.auth.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashMap;

@Getter
public class TokenDto implements Serializable {

	@NotBlank
	private final String token;

	@NotBlank
	private final String type;

	private final HashMap<String, Object> userDetails;
	public TokenDto(String token, String type, User user) {
		this.token = token;
		this.type = type;
		this.userDetails = new HashMap<>();
		this.userDetails.put("id", user.getId());
		this.userDetails.put("name", user.getName());
		this.userDetails.put("lastname", user.getLastname());
	}
}
