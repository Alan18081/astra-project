package com.alex.astraproject.apigateway.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SIGN_UP_COMPANY_URL = "/companies";
    public static final String LOGIN_EMPLOYEE_URL = "/login/employee";
}
