package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@Configuration
@EnableBinding(EmployeeEventsProcessor.class)
public class EmployeesController {

    @Autowired
    private EmployeesService employeesService;

    @Autowired
    private EmployeeEventsProcessor processor;

    @PostMapping
    public void createOne(@RequestBody @Valid CreateEmployeeCommand command) {
        EmployeeEvent event = employeesService.createEmployeeCommand(command);
        processor.created().send(MessageBuilder.withPayload(event).build());
    }

    @GetMapping("{id}/events")
    public List<EmployeeEvent> findManyEvents(@PathVariable UUID id, @RequestParam("revisionFrom") int revisionFrom) {
        return employeesService.findManyEventsById(id, revisionFrom);
    }

}
