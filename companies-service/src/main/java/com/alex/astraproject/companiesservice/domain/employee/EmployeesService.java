package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.employee.commands.CreateEmployeeCommand;
import com.alex.astraproject.shared.events.EmployeeEventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeesService {

    @Autowired
    private EmployeeEventsRepository employeeEventsRepository;

    public List<EmployeeEvent> findManyEventsById(UUID employeeId, int revisionFrom) {
        return employeeEventsRepository.findAllByEmployeeIdAndRevisionGreaterThan(employeeId, revisionFrom);
    }

    public EmployeeEvent createEmployeeCommand(CreateEmployeeCommand command) {
        UUID entityId = UUID.randomUUID();
        EmployeeEvent event = new EmployeeEvent(entityId, command, EmployeeEventType.CREATED, 1);
        return employeeEventsRepository.save(event);
    }

}
