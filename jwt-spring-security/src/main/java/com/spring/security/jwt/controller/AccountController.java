package com.spring.security.jwt.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.entity.Account;
import com.spring.security.jwt.entity.JWTTokenResponse;
import com.spring.security.jwt.repository.AccountRepository;
import com.spring.security.jwt.util.JwtTokenUtil;

@RestController
@CrossOrigin(origins = { "http://localhost:4200" })
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
	public ResponseEntity<Account> create(@RequestBody Account account) {
		if (null != account.getPassword() && !account.getPassword().isEmpty()) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}

		accountRepository.save(account);

		return ResponseEntity.status(HttpStatus.CREATED).body(account);
	}

	@PostMapping("authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Account account) {
		authenticate(account.getEmail(), account.getPassword());

		UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());

		return ResponseEntity.ok(new JWTTokenResponse(JwtTokenUtil.getGenerateToken(userDetails)));
	}

	@GetMapping("users")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Account> findAll() {
		return accountRepository.findAll();
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
