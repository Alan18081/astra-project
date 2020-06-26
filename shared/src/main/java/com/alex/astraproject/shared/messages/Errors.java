package com.alex.astraproject.shared.messages;

public class Errors {
  public static final String INVALID_COMPANY_PASSWORD = "Wrong password for company";
  public static final String COMPANY_NOT_FOUND_BY_ID = "Company with provided ID is not found";
  public static final String COMPANY_WITH_EMAIL_ALREADY_EXISTS = "Company with provided email already exists";
  public static final String COMPANY_NOT_FOUND_BY_EMAIL = "Company with provided email is not found";
  public static final String INVALID_COMPANY_AUTH_TOKEN = "Provided company auth token is invalid";

  public static final String INVALID_EMPLOYEE_PASSWORD = "Wrong password for position";
  public static final String EMPLOYEE_NOT_FOUND_BY_ID = "Employee with provided ID is not found";
  public static final String EMPLOYEE_NOT_FOUND_BY_EMAIL = "Employee with provided email is not found";
  public static final String EMPLOYEE_WITH_EMAIL_ALREADY_EXISTS = "Employee with provided email already exists";
	public static final String EMPLOYEE_HAS_WRONG_STATUS = "Employee cannot be added to project due to the it's wrong status";

  public static final String POSITION_NOT_FOUND_BY_ID = "Position with provided ID is not found";

  public static final String PROJECT_NOT_FOUND_BY_ID = "Project with provided ID is not found";
  public static final String PROJECT_ALREADY_COMPLETED = "Project already completed";
  public static final String PROJECT_ALREADY_STOPPED = "Project already stopped";
  public static final String PROJECT_IS_NOT_STOPPED = "Project is not stopped, that's why it cannot be resumed";
  public static final String PROJECT_DOES_NOT_HAVE_REQUIRED_POSITION = "Project doesn't have required position";
  public static final String PROJECT_ALREADY_HAVE_POSITION = "Project already have position";
	public static final String PROJECT_DOES_NOT_HAVE_REQUIRED_EMPLOYEE = "Project doesn't have required position";

  public static final String SPRINT_NOT_FOUND_BY_ID = "Sprint with provided ID is not found";
  public static final String SPRINT_ALREADY_HAS_STATUS = "Sprint already has status with provided name";
  public static final String SPRINT_ALREADY_COMPLETED = "Sprint already completed";

  public static final String TASK_NOT_FOUND_BY_ID = "Task with provided ID is not found";
  public static final String TASK_ALREADY_HAS_PROVIDED_STATUS = "Task already has provided status";
  public static final String TASK_ALREADY_ASSIGNED_TO_PROVIDED_EMPLOYEE = "Task already assigned to provided employee";

  public static final String AUTH_HEADER_NOT_FOUND = "Authorization header is missing";
  public static final String AUTH_HEADER_CONTENT_NOT_FOUND = "Authorization header content is missing";
  public static final String AUTH_HEADER_BEARER_NOT_FOUND = "Bearer prefix is needed";
  public static final String MALFORMAT_AUTH_HEADER = "Malformat Authorization content";

}
