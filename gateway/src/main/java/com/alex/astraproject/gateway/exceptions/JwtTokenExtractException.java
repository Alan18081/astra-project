package com.alex.astraproject.gateway.exceptions;


public class JwtTokenExtractException extends RuntimeException {
	public JwtTokenExtractException(String message) {
		super(message);
	}
}
