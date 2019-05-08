package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.clients.EmployeeClient;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventEntity;
import com.alex.astraproject.companiesservice.domain.employee.EmployeeEventsRepository;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.employees.EmployeeNotFoundException;
import com.alex.astraproject.shared.interfaces.EventsService;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.services.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface EmployeeService extends EventsService<EmployeeEventEntity> {

    Mono<EmployeeEventEntity> createEmployeeCommand(CreateEmployeeCommand command);

    Mono<EmployeeEventEntity> updateEmployeeCommand(UpdateEmployeeCommand command);

    Mono<EmployeeEventEntity> deleteEmployeeCommand(DeleteEmployeeCommand command);

}
