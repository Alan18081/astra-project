package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends PagingAndSortingRepository<EmployeeEntity, Long> {

    EmployeeEntity findByEmail(String email);

    Page<EmployeeEntity> findAllByCompany(CompanyEntity companyEntity, Pageable pageable);

}
