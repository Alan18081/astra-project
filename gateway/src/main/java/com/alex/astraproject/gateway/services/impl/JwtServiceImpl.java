package com.alex.astraproject.gateway.services.impl;

//import com.alex.astraproject.gateway.security.User;
import com.alex.astraproject.gateway.services.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtServiceImpl implements JwtService {

	@Value("${app.jwt.secret}")
	private String secret;

	@Value("${app.jwt.expiration}")
	private String expirationTime;

	@Override
	public String getUsernameFromToken(String token) {
		return getAllClaimsFromToken(token).getSubject();
	}

	@Override
	public Date getExpirationDateFromToken(String token) {
		return getAllClaimsFromToken(token).getExpiration();
	}

	@Override
	public Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
			.setSigningKey(secret)
			.parseClaimsJws(token)
			.getBody();
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	@Override
	public String generateToken(Object user) {
		Map<String, Object> claims = new HashMap<>();
//		claims.put("role", user.getRoles());
		return doGenerateToken(claims, "Some str");
	}

	private String doGenerateToken(Map<String,Object> claims, String username) {
		long expirationTimeLong = Long.parseLong(expirationTime);

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

		return Jwts.builder()
			.setClaims(claims)
			.setSubject(username)
			.setIssuedAt(createdDate)
			.setExpiration(expirationDate)
			.signWith(SignatureAlgorithm.HS512, secret)
			.compact();
	}

	@Override
	public boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
}
