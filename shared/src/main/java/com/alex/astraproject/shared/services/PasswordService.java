package com.alex.astraproject.shared.services;

public interface PasswordService {

	String encryptPassword(String password);

	boolean comparePassword(String password, String encrypted);

}
