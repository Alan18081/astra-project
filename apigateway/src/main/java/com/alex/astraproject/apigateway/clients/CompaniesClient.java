
package com.alex.astraproject.apigateway.clients;

import com.alex.astraproject.shared.events.EmployeeEvent;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Component
@FeignClient(value = "companies-service")
@RibbonClient(name = "companies-service")
public interface CompaniesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/employees/{id}/events?revisionFrom={revisionFrom}", consumes = "application/json")
    List<EmployeeEvent> findManyEventsById(
            @PathVariable("id") UUID id,
            @PathVariable("revisionFrom") int revisionFrom
    );

//    @RequestMapping(method = RequestMethod.GET, value = "/companies?page={page}&limit={limit}", consumes = "application/json")
//    List<Company> findMany(@PathVariable("page") int page, @PathVariable("limit") int limit);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
//    Company findById(@PathVariable("id") long id);
//
//    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}", consumes = "application/json")
//    Company findByEmail(@PathVariable("email") String email);
//
//    @RequestMapping(method = RequestMethod.POST, value = "/companies", consumes = "application/json")
//    Company createOne(CreateCompanyDto dto);
//
//    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = "application/json")
//    Company updateById(@PathVariable("id") long id, UpdateCompanyDto dto);
//
//    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}", consumes = "application/json")
//    Company removeById(@PathVariable("id") long id);
}
