package com.shopping.app.utils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.shopping.app.config.SecurityConstants;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public final class JwtTokenUtil {

	private JwtTokenUtil() {
	}

	public static String getGenerateToken(UserDetails user) {
		List<String> roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());

		byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

		String token = Jwts.builder().signWith(Keys.hmacShaKeyFor(signingKey), SignatureAlgorithm.HS512)
				.setHeaderParam("typ", SecurityConstants.TOKEN_TYPE).setIssuer(SecurityConstants.TOKEN_ISSURE)
				.setAudience(SecurityConstants.TOKEN_AUDIENCE).setSubject(user.getUsername())
				.setExpiration(new Date(System.currentTimeMillis() + 864000000)).claim("rol", roles).compact();

		return SecurityConstants.TOKEN_PREFIX.concat(token);
	}
}
