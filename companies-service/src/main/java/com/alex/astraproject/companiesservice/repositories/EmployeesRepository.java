package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeesRepository extends CrudRepository<Employee, Long> {

    Employee findByEmail(String email);

    List<Employee> findAllByCompany(long companyId);

}
