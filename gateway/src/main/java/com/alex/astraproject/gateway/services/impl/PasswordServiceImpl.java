package com.alex.astraproject.gateway.services.impl;

import com.alex.astraproject.gateway.services.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
	private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Override
	public String encryptPassword(String password) {
		return encoder.encode(password);
	}

	@Override
	public boolean comparePassword(String password, String encrypted) {
		return encoder.matches(password, encrypted);
	}
}
