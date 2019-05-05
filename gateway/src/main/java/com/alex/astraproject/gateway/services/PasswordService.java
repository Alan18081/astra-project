package com.alex.astraproject.gateway.services;

public interface PasswordService {

	String encryptPassword(String password);

	boolean comparePassword(String password, String encrypted);

}
