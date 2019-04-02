package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.EmployeeEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeesRepository extends CrudRepository<EmployeeEntity, Long> {

    EmployeeEntity findByEmail(String email);

    List<EmployeeEntity> findAllByCompanyId(long companyId);

}
