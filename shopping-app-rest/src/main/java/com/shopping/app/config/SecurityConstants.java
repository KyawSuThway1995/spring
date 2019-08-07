package com.shopping.app.config;

public interface SecurityConstants {

	String AUTH_LOGIN_URL = "/api/authenticate";
	String JWT_SECRET = "nZr4u7w!z%C*F-JaNdRgUkXp2s5v8y/A?D(G+KbPeShVmYq3t6w9z$C&E)H@McQf";
	String TOKEN_HEADER = "Authorization";
	String TOKEN_PREFIX = "Bearer ";
	String TOKEN_TYPE = "JWT";
	String TOKEN_ISSURE = "secure-api";
	String TOKEN_AUDIENCE = "secure-app";
}
