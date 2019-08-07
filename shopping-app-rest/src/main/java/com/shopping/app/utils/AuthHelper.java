package com.shopping.app.utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

	public String getLoginUserName() {
		SecurityContext context = SecurityContextHolder.getContext();
		return context.getAuthentication().getName();
	}

	public boolean isUserInRole(String role) {
		SecurityContext context = SecurityContextHolder.getContext();
		for (GrantedAuthority ga : context.getAuthentication().getAuthorities()) {
			if (ga.getAuthority().equals(role))
				return true;
		}

		return false;
	}
}
