package com.alex.astraproject.shared.security;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 864000000;
    public static final String HEADER_NAME = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String SIGN_UP_COMPANY_URL = "/companies-service/companies";
    public static final String LOGIN_COMPANY_URL = "/companies-service/companies/login";
    public static final String LOGIN_EMPLOYEE_URL = "/login/employee";
}
