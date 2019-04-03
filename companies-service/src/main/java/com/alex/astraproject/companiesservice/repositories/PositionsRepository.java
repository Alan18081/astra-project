package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import com.alex.astraproject.companiesservice.entities.PositionEntity;
import com.alex.astraproject.shared.entities.Position;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionsRepository extends PagingAndSortingRepository<PositionEntity, Long> {

    Page<PositionEntity> findAllByCompanyEntity(CompanyEntity companyEntity, Pageable pageable);

    PositionEntity findByName(String name);

    List<PositionEntity> findAllByAverageSalaryIsGreaterThanAndAverageSalaryIsLessThan(double salaryFrom, double salaryTo);


}
