package com.spring.security.jwt.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spring.security.jwt.entity.Account;
import com.spring.security.jwt.repository.AccountRepository;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Account account = accountRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email + " Not Found!"));
		 
		 return new User(account.getEmail(), account.getPassword(), getAuthrities(account));
	}

	private Collection<? extends GrantedAuthority> getAuthrities(Account account){
		
		String[] roles = account.getRoles().stream().toArray(String[]::new);
		
		List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
		
		return authorities;
	}
}
