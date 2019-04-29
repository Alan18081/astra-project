package com.alex.astraproject.queryservice.domain.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public Flux<CompanyEntity> findMany() {
        return companyService.findMany();
    }

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<CompanyEntity>> findOneByEmail(@PathVariable String email) {
        return companyService.findOneByEmail(email)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CompanyEntity>> findOneById(@PathVariable UUID id) {
        return companyService.findOneById(id)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
