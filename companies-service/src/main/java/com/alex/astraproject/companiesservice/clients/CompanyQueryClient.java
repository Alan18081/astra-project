package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Company;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Component
@FeignClient(name = "query-service")
@RibbonClient(name = "query-service")
public interface CompanyQueryClient {

	@GetMapping("/{id}")
	Company findOneById(@PathVariable("id") String id);

	@GetMapping("/companies/email/{email}")
	Company findOneByEmail(@PathVariable("email") String email);

}
