package com.shopping.app.repository;

import com.shopping.app.entity.Account;
import com.shopping.app.repository.custom.BaseRepository;

public interface AccountRepository extends BaseRepository<Account, String>{
	Account findByEmail(String email);
}
