package com.alex.astraproject.gateway.exceptions;


import org.springframework.security.core.AuthenticationException;

public class JwtTokenExtractException extends AuthenticationException {
	public JwtTokenExtractException(String message) {
		super(message);
	}
}
