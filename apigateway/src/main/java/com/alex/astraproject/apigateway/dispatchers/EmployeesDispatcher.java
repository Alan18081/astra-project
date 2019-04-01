package com.alex.astraproject.apigateway.dispatchers;

import com.alex.astraproject.apigateway.entities.Employee;

import java.util.List;

public interface EmployeesDispatcher {

    List<Employee> findMany(long companyId);

}
