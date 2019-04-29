package com.alex.astraproject.shared.messages;

public class Errors {
    public static final String COMPANY_NOT_FOUND_BY_ID = "Company with provided ID is not found";
    public static final String COMPANY_WITH_EMAIL_ALREADY_EXISTS = "Company with provided email already exists";
    public static final String COMPANY_NOT_FOUND_BY_EMAIL = "Company with provided email is not found";
    public static final String INVALID_COMPANY_AUTH_TOKEN = "Provided company auth token is invalid";

    public static final String EMPLOYEE_NOT_FOUND_BY_ID = "Employee with provided ID is not found";
    public static final String EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS = "Employee with provided email already exists";
    public static final String POSITION_NOT_FOUND_BY_ID = "Position with provided ID is not found";

    public static final String AUTH_HEADER_NOT_FOUND = "Authorization header is missing";
    public static final String AUTH_HEADER_CONTENT_NOT_FOUND = "Authorization header content is missing";
    public static final String AUTH_HEADER_BEARER_NOT_FOUND = "Bearer prefix is needed";
    public static final String MALFORMAT_AUTH_HEADER = "Malformat Authorization content";

}
