package com.shopping.app.repository.custom;

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

import com.shopping.app.entity.Account;
import com.shopping.app.repository.AccountRepository;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email);

		return new User(account.getEmail(), account.getPassword(), getAuthrities(account));
	}

	private Collection<? extends GrantedAuthority> getAuthrities(Account account) {

		List<GrantedAuthority> authorities = AuthorityUtils
				.createAuthorityList(new String[] { account.getRole().name() });

		return authorities;
	}
}
