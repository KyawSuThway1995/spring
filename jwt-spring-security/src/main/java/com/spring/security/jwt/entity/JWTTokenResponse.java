package com.spring.security.jwt.entity;

import java.io.Serializable;

public class JWTTokenResponse implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String token;
	
	public JWTTokenResponse(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}
