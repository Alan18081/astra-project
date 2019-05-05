package com.alex.astraproject.queryservice.domain.employee;

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
@RequestMapping("/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public Flux<EmployeeEntity> findMany() {
        return employeeService.findMany();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<EmployeeEntity>> findOneById(@PathVariable String id) {
        return employeeService.findById(id)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @GetMapping("/email/{email}")
    public Mono<ResponseEntity<EmployeeEntity>> findOneByEmail(@PathVariable String email) {
        return employeeService.findOneByEmail(email)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
