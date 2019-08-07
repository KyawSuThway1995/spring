package com.shopping.app.utils;

public class JWTResponse {

	private String name;
	private String role;
	private String token;

	public JWTResponse(String name, String role, String token) {
		super();
		this.name = name;
		this.role = role;
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public String getRole() {
		return role;
	}

	public String getToken() {
		return token;
	}

}
