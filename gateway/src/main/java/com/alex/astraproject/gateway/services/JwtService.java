package com.alex.astraproject.gateway.services;

//import com.alex.astraproject.gateway.security.User;
import io.jsonwebtoken.Claims;

import java.util.Date;

public interface JwtService {

	String getUsernameFromToken(String token);

	Date getExpirationDateFromToken(String token);

	Claims getAllClaimsFromToken(String token);

	String generateToken(Object user);

	boolean validateToken(String token);
}
