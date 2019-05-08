package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private EmployeeMessagesService employeeMessagesService;

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> deleteOne(@PathVariable UUID id) {
    return employeeService
	    .deleteEmployeeCommand(new DeleteEmployeeCommand(id))
      .flatMap(event -> {
        employeeMessagesService.sendFiredEmployeeEvent(event);
        return Mono.empty();
      });
  }

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> updateOne(@PathVariable UUID id, @RequestBody @Valid UpdateEmployeeCommand command) {
    command.setEmployeeId(id);
    return employeeService
      .updateEmployeeCommand(command)
      .flatMap(event -> {
          employeeMessagesService.sendUpdatedEmployeeEvent(event);
          return Mono.empty();
      });
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreateEmployeeCommand command) {
    return employeeService
      .createEmployeeCommand(command)
      .flatMap(event -> {
          employeeMessagesService.sendCreatedEmployeeEvent(event);
          return Mono.empty();
      });
  }

  @GetMapping("{id}/events")
  public Flux<EmployeeEventEntity> findManyEvents(
    @PathVariable String id,
    GetEventsDto dto
  ) {
    dto.setEntityId(id);
    return employeeService.getEvents(dto);
  }

}
