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

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public Flux<CompanyEntity> findMany() {
        return companyService.findMany(null);
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<CompanyEntity>> findOneById(@PathVariable String id) {
        return companyService.findById(id)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<CompanyEntity>> findOneByEmail(@PathVariable String email) {
        return companyService.findOneByEmail(email)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



}
