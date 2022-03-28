package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.exception.security.UnauthorizedException;
import dh.projetointegradorctd.backend.repository.UserRepository;
import dh.projetointegradorctd.backend.request.SignUpForm;
import dh.projetointegradorctd.backend.request.SignInForm;
import dh.projetointegradorctd.backend.dto.TokenDto;
import dh.projetointegradorctd.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/auth", produces = "application/json;charset=UTF-8")
public class AuthenticationController {

	@Autowired
	private AuthenticationService authService;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/sign-in")
	public ResponseEntity<TokenDto> signIn (@RequestBody @Valid SignInForm form) throws UnauthorizedException {
		return ResponseEntity.ok(authService.signIn(form));
	}

	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp (@RequestBody SignUpForm form) {
		authService.signUp(form);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

//  TODO - Implementar serviço de checagem de email para validar cadastro
//	@GetMapping("check-email/{userId}/{emailHash}")
//	public ResponseEntity<Boolean> check(@PathVariable long userId, @PathVariable int emailHash) {
//		return ResponseEntity.ok(authService.checkEmail(userId, emailHash));
//	}
}
