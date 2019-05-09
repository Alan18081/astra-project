package com.alex.astraproject.companiesservice.domain.employee.impl;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.clients.EmployeeClient;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventsRepository;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeService;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeEventsRepository employeeEventsRepository;

    @Autowired
    private CompanyClient companyClient;

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
		PasswordService passwordService;

    public Flux<EmployeeEventEntity> findManyEventsById(String employeeId, int revisionFrom) {
      return employeeEventsRepository.findAllByEmployeeIdAndRevisionGreaterThan(employeeId, revisionFrom);
    }

    public Mono<EmployeeEventEntity> createEmployeeCommand(CreateEmployeeCommand command) {
      return companyClient.findCompanyById(command.getCompanyId())
        .flatMap(company -> employeeClient.isEmployeeExists(command.getEmail()))
        .flatMap(isExists -> {
          if(isExists) {
            return Mono.error(new EmployeeAlreadyExistsException());
          }
          String entityId = UUID.randomUUID().toString();
	        command.setPassword(passwordService.encryptPassword(command.getPassword()));
          EmployeeEventEntity event = EmployeeEventEntity.builder()
            .id(null)
            .employeeId(entityId)
            .type(EmployeeEventType.CREATED)
            .data(command)
            .revision(1)
            .build();
          return employeeEventsRepository.save(event);
        })
        .switchIfEmpty(Mono.error(new NotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)));
    }

    public Mono<EmployeeEventEntity> updateEmployeeCommand(UpdateEmployeeCommand command) {
      return employeeClient.findEmployeeById(command.getEmployeeId())
        .flatMap(employee -> {
          if(employee == null) {
            return Mono.error(new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID));
          }
          return employeeEventsRepository
            .findFirstByEmployeeIdOrderByRevisionDesc(employee.getId());
        })
        .flatMap(eventEntity -> {
          EmployeeEventEntity event = EmployeeEventEntity.builder()
          .id(null)
          .employeeId(command.getEmployeeId())
          .type(EmployeeEventType.UPDATED)
          .data(command)
          .revision(eventEntity.getRevision() + 1)
          .build();

          return employeeEventsRepository.save(event);
        });
    }

    public Mono<EmployeeEventEntity> deleteEmployeeCommand(DeleteEmployeeCommand command) {
      return employeeEventsRepository
        .findFirstByEmployeeIdOrderByRevisionDesc(command.getEmployeeId())
        .flatMap(eventEntity -> {
          if(eventEntity == null){
            return Mono.error(new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID));
          }
          EmployeeEventEntity event = EmployeeEventEntity.builder()
            .id(null)
            .employeeId(command.getEmployeeId())
            .type(EmployeeEventType.FIRED)
            .data(null)
            .revision(eventEntity.getRevision() + 1)
            .build();
          return employeeEventsRepository.save(event);
        });
    }

  @Override
  public Flux<EmployeeEventEntity> getEvents(GetEventsDto dto) {
    return null;
  }
}
