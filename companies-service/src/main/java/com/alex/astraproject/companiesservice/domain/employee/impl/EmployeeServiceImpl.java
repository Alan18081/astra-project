package com.alex.astraproject.companiesservice.domain.employee.impl;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.clients.EmployeeQueryClient;
import com.alex.astraproject.companiesservice.clients.PositionQueryClient;
import com.alex.astraproject.companiesservice.domain.company.CompanyEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventsRepository;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeService;
import com.alex.astraproject.companiesservice.domain.employee.commands.ChangeEmployeePositionCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
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

import java.util.Date;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

  @Autowired
  private EmployeeEventsRepository employeeEventsRepository;

  @Autowired
  private CompanyQueryClient companyQueryClient;

  @Autowired
  private EmployeeQueryClient employeeQueryClient;

  @Autowired
  private PositionQueryClient positionQueryClient;

  @Autowired
  PasswordService passwordService;

  public Mono<EmployeeEventEntity> createEmployeeCommand(CreateEmployeeCommand command) {
    return companyQueryClient.findCompanyById(command.getCompanyId())
      .flatMap(company -> employeeQueryClient.isEmployeeExists(command.getEmail()))
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
    return employeeQueryClient.findEmployeeById(command.getEmployeeId())
      .flatMap(employee -> buildEvent(employee, EmployeeEventType.UPDATED, command));
  }

  public Mono<EmployeeEventEntity> deleteEmployeeCommand(DeleteEmployeeCommand command) {
    return employeeQueryClient.findEmployeeById(command.getEmployeeId())
      .flatMap(employee -> buildEvent(employee, EmployeeEventType.FIRED, command));
  }

  @Override
  public Mono<EmployeeEventEntity> changeEmployeePosition(ChangeEmployeePositionCommand command) {
    return employeeQueryClient.findEmployeeById(command.getEmployeeId())
      .switchIfEmpty(Mono.error(new NotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID)))
      .flatMap(employee ->
        positionQueryClient.findPositionById(command.getPositionId())
          .switchIfEmpty(Mono.error(new NotFoundException(Errors.POSITION_NOT_FOUND_BY_ID)))
          .flatMap(position -> buildEvent(employee, EmployeeEventType.CHANGED_POSITION, command))
      );
  }

  private Mono<EmployeeEventEntity> buildEvent(Employee employee, String eventType, Object command) {
    return employeeEventsRepository.findFirstByEmployeeIdOrderByRevisionDesc(employee.getId())
      .flatMap(companyEventEntity -> {
        EmployeeEventEntity event = EmployeeEventEntity.builder()
          .id(null)
          .employeeId(employee.getId())
          .type(eventType)
          .data(command)
          .revision(companyEventEntity.getRevision() + 1)
          .build();
        return employeeEventsRepository.save(event);
      });
  }

  @Override
  public Flux<EmployeeEventEntity> getEvents(GetEventsDto dto) {
    return employeeEventsRepository.findManyEvents(dto);
  }
}
