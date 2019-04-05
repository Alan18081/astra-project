package com.alex.astraproject.companiesservice.domain.employee;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<EmployeeEntity, Long> {

    EmployeeEntity findByEmail(String email);

    Page<EmployeeEntity> findAllByCompany(CompanyEntity companyEntity, Pageable pageable);

}
