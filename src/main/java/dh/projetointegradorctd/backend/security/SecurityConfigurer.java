package dh.projetointegradorctd.backend.security;

import dh.projetointegradorctd.backend.model.auth.Role;
import dh.projetointegradorctd.backend.repository.UserRepository;
import dh.projetointegradorctd.backend.service.UserDetailsServiceImpl;
import dh.projetointegradorctd.backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Value("${api-base-path}")
	private String basePath;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String ADMIN = Role.Authority.ADMIN.name();
		String CLIENT = Role.Authority.CLIENT.name();

		http.cors()
				.and().authorizeRequests()
				.antMatchers( basePath + "/users", basePath + "/roles").hasAuthority(ADMIN)
				.antMatchers(HttpMethod.POST, basePath + "/bookings").hasAuthority(CLIENT)
				.antMatchers(HttpMethod.POST, basePath + "/auth/sign-in").permitAll()
				.antMatchers(HttpMethod.POST, basePath + "/auth/sign-up").permitAll()
				.antMatchers(HttpMethod.POST, basePath + "/products/evaluations").hasAuthority(CLIENT)
				.antMatchers(HttpMethod.GET).permitAll()
				.anyRequest().hasAuthority(ADMIN)
				.and().addFilterBefore(
						new TokenAuthenticationFilter(tokenService, userRepository),
						UsernamePasswordAuthenticationFilter.class
				)
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and().csrf().disable();
	}
}
