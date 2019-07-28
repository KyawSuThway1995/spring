package com.spring.security.jwt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.security.jwt.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer>{

	Optional<Account> findByEmail(String email);
}
