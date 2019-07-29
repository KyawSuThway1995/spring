package com.spring.security.jwt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.security.jwt.entity.Account;
import com.spring.security.jwt.repository.AccountRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class AccountController {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping
	public String create(@RequestBody Account account) {
		if(null != account.getPassword() && !account.getPassword().isEmpty()) {
			account.setPassword(passwordEncoder.encode(account.getPassword()));
		}
		
		accountRepository.save(account);
		
		return "Account was created";
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public List<Account> findAll(){
		return accountRepository.findAll();
	}
}
