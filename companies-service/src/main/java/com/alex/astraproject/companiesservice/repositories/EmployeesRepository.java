package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.Company;
import com.alex.astraproject.companiesservice.entities.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends CrudRepository<Employee, Long> {

    Employee findByEmail(String email);

    List<Employee> findAllByCompanyId(long companyId);

}
