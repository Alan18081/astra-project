package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.clients.EmployeeQueryClient;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeesService {

    @Autowired
    private EmployeeEventsRepository employeeEventsRepository;

    @Autowired
    private EmployeeQueryClient employeeQueryClient;

    @Autowired
    private CompanyQueryClient companyQueryClient;

    public Flux<EmployeeEventEntity> findManyEventsById(UUID employeeId, int revisionFrom) {
      return employeeEventsRepository.findAllByEmployeeIdAndRevisionGreaterThan(employeeId, revisionFrom);
    }

    public Mono<EmployeeEventEntity> createEmployeeCommand(CreateEmployeeCommand command) {
        Mono<Company> companyMono = Mono.justOrEmpty(companyQueryClient.findOneById(command.getCompanyId().toString()));
        return companyMono.flatMap(company -> {
          if(company == null) {
            return Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID));
          }
          UUID entityId = UUID.randomUUID();
          EmployeeEventEntity event = new EmployeeEventEntity(null, entityId, EmployeeEventType.CREATED, command, 1);
          return employeeEventsRepository.save(event);
        });
    }

    public Mono<EmployeeEventEntity> updateEmployeeCommand(UpdateEmployeeCommand command) {
	    Mono<Employee> employeeMono = Mono.justOrEmpty(employeeQueryClient.findOneById(command.getEmployeeId().toString()));
      return employeeMono.flatMap(employee -> {
        if(employee == null) {
          return Mono.error(new EmployeeNotFoundException(Errors.EMPLOYEE_NOT_FOUND_BY_ID));
        }
        return employeeEventsRepository
          .findFirstByEmployeeIdOrderByRevisionDesc(employee.getId());
      })
      .flatMap(eventEntity -> {
	      EmployeeEventEntity event = new EmployeeEventEntity(
		      null,
		      command.getEmployeeId(),
		      EmployeeEventType.UPDATED,
		      command,
		      eventEntity.getRevision() + 1
	      );
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
          EmployeeEventEntity event = new EmployeeEventEntity(
            null,
            command.getEmployeeId(),
            EmployeeEventType.FIRED,
            null,
            eventEntity.getRevision() + 1
          );
          return employeeEventsRepository.save(event);
        });
    }

}
