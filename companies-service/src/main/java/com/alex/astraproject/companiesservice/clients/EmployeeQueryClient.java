package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Employee;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Component
@FeignClient(name = "query-service")
@RibbonClient(name = "query-service")
@RequestMapping("/employees")
public interface EmployeeQueryClient {

	@GetMapping("/{id}")
	Optional<Employee> findOneById(@PathVariable("id") String id);

}
