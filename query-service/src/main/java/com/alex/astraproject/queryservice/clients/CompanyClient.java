package com.alex.astraproject.queryservice.clients;

import com.alex.astraproject.shared.events.CompanyEvent;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;

@Component
@FeignClient(name = "companies-service")
@RibbonClient
@RequestMapping("/companies")
public interface CompanyClient {

	@GetMapping("/{id}/events?revisionFrom={revisionFrom}")
	Flux<CompanyEvent> findManyEvents(@PathVariable("id") String id, @PathVariable("revisionFrom") int revisionFrom);

}
