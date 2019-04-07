package com.alex.astraproject.companiesservice.domain.employee;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface EmployeeEventsRepository extends MongoRepository<EmployeeEvent, String> {

    List<EmployeeEvent> findAllByEmployeeIdAndRevisionGreaterThan(UUID employeeId, int revision);

}
