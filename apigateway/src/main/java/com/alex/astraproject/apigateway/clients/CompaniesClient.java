
package com.alex.astraproject.apigateway.clients;

import com.alex.astraproject.shared.dto.employees.CreateEmployeeDto;
import com.alex.astraproject.shared.dto.employees.UpdateEmployeeDto;
import com.alex.astraproject.shared.entities.Company;
import feign.Param;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient("companies-client")
@RibbonClient(name = "companies-service")
public interface CompaniesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/companies?page={page}&limit={limit}", consumes = "application/json")
    List<Company> findMany(@PathVariable("page") int page, @PathVariable("limit") int limit);

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    Company findById(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}", consumes = "application/json")
    Company findByEmail(@PathVariable String email);

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = "application/json")
    Company createOne(CreateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
    Company updateOne(@PathVariable long id, UpdateEmployeeDto dto);

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
    Company removeById(@PathVariable long id);
}
