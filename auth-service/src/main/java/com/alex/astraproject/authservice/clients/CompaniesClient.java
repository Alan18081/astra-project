package com.alex.astraproject.authservice.clients;

import com.alex.astraproject.shared.entities.Company;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
@FeignClient(value = "companies-service")
@RibbonClient(name = "companies-service")
public interface CompaniesClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", consumes = "application/json")
    Company findById(@PathVariable("id") long id);

    @RequestMapping(method = RequestMethod.GET, value = "/email/{email}", consumes = "application/json")
    Company findByEmail(@PathVariable("email") String email);

}
