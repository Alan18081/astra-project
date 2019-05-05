package com.alex.astraproject.queryservice.domain.employee;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends PagingAndSortingRepository<EmployeeEntity, String> {

	Optional<EmployeeEntity> findFirstByEmail(String email);

}
