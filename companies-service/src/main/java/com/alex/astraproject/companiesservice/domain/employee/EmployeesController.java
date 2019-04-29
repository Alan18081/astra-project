package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.DeleteEmployeeCommand;
import com.alex.astraproject.companiesservice.domain.employee.commands.UpdateEmployeeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
public class EmployeesController {

  @Autowired
  private EmployeesService employeesService;

  @Autowired
  private EmployeeMessagesService employeeMessagesService;

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> deleteOne(@PathVariable UUID id) {
    return employeesService
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
    return employeesService
      .updateEmployeeCommand(command)
      .flatMap(event -> {
          employeeMessagesService.sendUpdatedEmployeeEvent(event);
          return Mono.empty();
      });
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreateEmployeeCommand command) {
    return employeesService
      .createEmployeeCommand(command)
      .flatMap(event -> {
          employeeMessagesService.sendCreatedEmployeeEvent(event);
          return Mono.empty();
      });
  }

  @GetMapping("{id}/events")
  public Flux<EmployeeEventEntity> findManyEvents(@PathVariable UUID id, @RequestParam("revisionFrom") int revisionFrom) {
    return employeesService.findManyEventsById(id, revisionFrom);
  }

}
