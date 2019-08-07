package com.shopping.app.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.app.entity.Account;
import com.shopping.app.repository.AccountRepository;
import com.shopping.app.utils.JWTResponse;
import com.shopping.app.utils.JwtTokenUtil;

@RestController
@CrossOrigin
@RequestMapping("api/account")
public class AccountController {
	
	@Autowired
	private AuthenticationManager auth;

	@Autowired
	private AccountRepository repo;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("create")
	public ResponseEntity<Account> signUp(@RequestBody Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		repo.save(account);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(account);
	}
	
	@PostMapping("sign-in")
	public ResponseEntity<?> signIn(@RequestBody Account account) {
		authenticate(account.getEmail(), account.getPassword());
		UserDetails userDetails = userDetailsService.loadUserByUsername(account.getEmail());
		return ResponseEntity.ok(new JWTResponse(userDetails.getUsername(), userDetails.getAuthorities().stream().findFirst().get().toString(), JwtTokenUtil.getGenerateToken(userDetails)));
	}
	

	private void authenticate(String username, String password) {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		auth.authenticate(new UsernamePasswordAuthenticationToken(username, password));
	}
	
}
