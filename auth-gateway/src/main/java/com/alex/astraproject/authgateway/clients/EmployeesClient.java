package com.alex.astraproject.authgateway.clients;

import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Employee;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Component
@FeignClient(value = "companies-service")
@RibbonClient(name = "companies-service")
public interface EmployeesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/commands?companyId={companyId}", consumes = "application/json")
    List<Employee> findManyByCompanyId(@PathVariable("companyId") long companyId);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    Employee findById(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    Employee createOne(CreateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    Employee updateById(@PathVariable("id") long id, UpdateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
    Employee removeById(@PathVariable("id") long id);
}
