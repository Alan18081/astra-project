package com.alex.astraproject.authservice.clients;

import com.alex.astraproject.authservice.entities.Employee;
import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "employees-service", url = "/employees")
public interface EmployeesClient {

    @RequestMapping(method = RequestMethod.GET, value = "", consumes = "application/json")
    List<Employee> findManyByCompanyId(@RequestParam("companyId") long companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    Employee findById(@PathVariable long id);

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    Employee createOne(CreateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    Employee updateById(@PathVariable long id, UpdateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
    Employee removeById(@PathVariable long id);
}
