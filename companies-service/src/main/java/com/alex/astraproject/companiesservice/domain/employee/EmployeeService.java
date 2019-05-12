package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.employee.commands.ChangeEmployeePositionCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface EmployeeService extends EventsService<EmployeeEventEntity> {

    Mono<EmployeeEventEntity> createEmployeeCommand(CreateEmployeeCommand command);

    Mono<EmployeeEventEntity> updateEmployeeCommand(UpdateEmployeeCommand command);

    Mono<EmployeeEventEntity> deleteEmployeeCommand(DeleteEmployeeCommand command);

    Mono<EmployeeEventEntity> changeEmployeePosition(ChangeEmployeePositionCommand command);

}
