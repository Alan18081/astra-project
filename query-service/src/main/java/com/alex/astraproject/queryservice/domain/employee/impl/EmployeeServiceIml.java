package com.alex.astraproject.queryservice.domain.employee.impl;

import com.alex.astraproject.queryservice.clients.EmployeeClient;
import com.alex.astraproject.queryservice.domain.employee.EmployeeEntity;
import com.alex.astraproject.queryservice.domain.employee.EmployeeRepository;
import com.alex.astraproject.queryservice.domain.employee.EmployeeService;
import com.alex.astraproject.queryservice.domain.employee.dto.FindManyEmployeesDto;
import com.alex.astraproject.shared.events.EmployeeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Service
public class EmployeeServiceIml implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeClient employeeClient;

    public Flux<EmployeeEntity> findMany() {
        return Flux.fromIterable(employeeRepository.findAll());
    }

    @Override
    public void createOne(EmployeeEvent event) {
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntity.replay(Arrays.asList(event));
        employeeRepository.save(employeeEntity);
    }

    @Override
    public void updateById(EmployeeEvent event) {
        Mono<EmployeeEntity> employeeEntityMono = Mono.justOrEmpty(employeeRepository.findById(event.getEmployeeId()));
        applyEvents(employeeEntityMono);
    }

    public Mono<EmployeeEntity> findOneByEmail(String email) {
        return Mono.justOrEmpty(employeeRepository.findFirstByEmail(email));
    }

    @Override
    public Flux<EmployeeEntity> findMany(FindManyEmployeesDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
        return Flux.fromIterable(employeeRepository.findAll(pageable));
    }

    @Override
    public Flux<EmployeeEntity> findManyThatWorksWithProvidedEmployee(String employeeId) {
        return Flux.fromIterable(employeeRepository.findManyThatWorksWithOne(employeeId));
    }

    @Override
    public Mono<EmployeeEntity> findById(String id) {
        return Mono.justOrEmpty(employeeRepository.findById(id));
    }

    private void applyEvents(Mono<EmployeeEntity> employeeEntityMono) {
        employeeEntityMono
          .subscribe(employeeEntity -> employeeClient
            .findEmployeeEventsById(employeeEntity.getId(), employeeEntity.getRevision())
            .doOnNext(employeeEntity::applyEvent)
            .thenMany(other -> employeeRepository.save(employeeEntity))
          );
    }
}
