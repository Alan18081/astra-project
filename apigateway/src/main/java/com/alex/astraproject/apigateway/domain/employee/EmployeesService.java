package com.alex.astraproject.apigateway.domain.employee;

import com.alex.astraproject.apigateway.clients.CompaniesClient;
import com.alex.astraproject.apigateway.domain.employee.dto.messages.CreatedEmployeeEventDto;
import com.alex.astraproject.shared.dto.employees.FindManyEmployeesDto;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.messages.Errors;
import com.alex.astraproject.shared.responses.PaginatedResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EmployeesService {

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private CompaniesClient companiesClient;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;

    public PaginatedResponse<EmployeeAggregate> findMany(FindManyEmployeesDto dto) {
        Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
        Page<EmployeeAggregate> employeeAggregatePage = employeesRepository.findAll(pageable);
        return new PaginatedResponse<>(
                employeeAggregatePage.getContent(),
                dto.getPage(),
                dto.getLimit(),
                employeeAggregatePage.getTotalElements()
        );
    }

    public EmployeeAggregate findById(UUID id) {
        return Optional.of(employeesRepository.findById(id))
                .get()
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, Errors.EMPLOYEE_NOT_FOUND_BY_ID));
    }

    public void createEmployee(CreatedEmployeeEventDto dto) {
        EmployeeAggregate employeeAggregate = new EmployeeAggregate();
        employeeAggregate.initialize(dto.getEmployeeId());
        List<EmployeeEvent> events = companiesClient.findManyEventsById(
                dto.getEmployeeId(), 0);
        employeeAggregate.replay(events);
        employeeAggregate.setRevision(0);
        employeesRepository.save(employeeAggregate);
    }

}
