package dh.projetointegradorctd.backend.controller;

import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import dh.projetointegradorctd.backend.request.SignUpForm;
import dh.projetointegradorctd.backend.request.SignInForm;
import dh.projetointegradorctd.backend.dto.TokenDto;
import dh.projetointegradorctd.backend.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controlador de autenticação
 */
@RestController
@RequestMapping(value = "/auth", produces = "application/json;charset=UTF-8")
public class AuthenticationController {

	/**
	 * Instância do serviço de autenticação autoinjetada
	 */
	@Autowired
	private AuthenticationService authService;

	/**
	 * Endpoint de cadastro
	 * @param form Formulário de cadastro contêndo email, senha, nome e sobrenome do usuário
	 * @return Resposta http sem conteúdo - status 201
	 */
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp (@RequestBody SignUpForm form) throws Exception {
		authService.signUp(form);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	/**
	 * Endpoint de autenticação
	 * @param form Formulário de login contêndo email e senha do usuário
	 * @return Resposta http contendo um token para a comunicação autenticada e detalhes do usuário
	 * @throws ForbiddenException Usuário não autorizado
	 */
	@PostMapping("/sign-in")
	public ResponseEntity<TokenDto> signIn (@RequestBody @Valid SignInForm form) throws ForbiddenException {
		return ResponseEntity.ok(authService.signIn(form));
	}

//  TODO - Implementar serviço de checagem de email para validar cadastro
//	@GetMapping("check-email/{userId}/{emailHash}")
//	public ResponseEntity<Boolean> check(@PathVariable long userId, @PathVariable int emailHash) {
//		return ResponseEntity.ok(authService.checkEmail(userId, emailHash));
//	}
}
