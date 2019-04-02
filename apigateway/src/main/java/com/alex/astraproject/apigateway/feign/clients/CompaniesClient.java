
package com.alex.astraproject.apigateway.feign.clients;

import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "companies-service", url = "/companies")
public interface CompaniesClient {

    @RequestMapping(method = RequestMethod.GET, value = "", consumes = "application/json")
    List<Company> findMany(@RequestParam("page") int page, @RequestParam("page") int limit);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    Company findById(@PathVariable long id);

    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}", consumes = "application/json")
    Company findByEmail(@PathVariable String email);

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    Company createOne(CreateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    Company updateOne(@PathVariable long id, UpdateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
    Company removeById(@PathVariable long id);
}
