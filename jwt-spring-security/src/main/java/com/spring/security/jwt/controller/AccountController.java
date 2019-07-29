package com.spring.security.jwt.controller;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.config.SecurityConstants;
import com.spring.security.jwt.entity.Account;
import com.spring.security.jwt.entity.JWTTokenResponse;
import com.spring.security.jwt.repository.AccountRepository;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/api")
public class AccountController {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDetailsService userDetailsService;

	@PostMapping("users")
	public String create(@RequestBody Account account) {
		if (null != account.getPassword() && !account.getPassword().isEmpty()) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}

		accountRepository.save(account);

		return "Account was created";
	}

	@PostMapping("authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Account account) {
		authenticate(account.getEmail(), account.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());

		return ResponseEntity.ok(new JWTTokenResponse(getJWTToken(userDetails)));
	}

	@GetMapping("users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	private String getJWTToken(UserDetails userDetails) {
		List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

		String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setIssuer(SecurityConstants.TOKEN_ISSURE)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE).setSubject(userDetails.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 864000000)).claim("rol", roles).compact();

		return token;
	}

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
