package com.alex.astraproject.apigateway.domain.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<EmployeeAggregate, UUID> {

    Page<EmployeeAggregate> findAll(Pageable pageable);

}
