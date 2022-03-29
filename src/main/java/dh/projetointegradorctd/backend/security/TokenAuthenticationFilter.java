package dh.projetointegradorctd.backend.security;

import dh.projetointegradorctd.backend.exception.security.ForbiddenException;
import dh.projetointegradorctd.backend.model.auth.User;
import dh.projetointegradorctd.backend.repository.UserRepository;
import dh.projetointegradorctd.backend.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UserRepository userRepository;

	public TokenAuthenticationFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.userRepository = repository;
	}

	@Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain
	) throws IOException, ServletException {
		
		String token = recoverToken(request);
		if(tokenService.isValid(token)) {
			try {
				authenticateUser(token);
			} catch (Exception e) {
				e.printStackTrace();
				response.getWriter().write("Nao autorizado");
				response.setStatus(HttpStatus.FORBIDDEN.value());
			}
		}
		filterChain.doFilter(request, response);
	}

	private void authenticateUser(String token) throws ForbiddenException {
		Long userId = tokenService.getUserIdFromToken(token);
		User user = userRepository.findById(userId).orElse(null);

		if(user == null) {
			throw new ForbiddenException();
		}

		UsernamePasswordAuthenticationToken authentication =
				new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recoverToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if (token == null || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7);
	}

}
