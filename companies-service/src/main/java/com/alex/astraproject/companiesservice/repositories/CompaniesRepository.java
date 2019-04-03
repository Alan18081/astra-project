package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompaniesRepository extends PagingAndSortingRepository<CompanyEntity, Long> {

    Optional<CompanyEntity> findById(long id);

    Optional<CompanyEntity> findByCorporateEmail(String email);

    void deleteById(long id);

}
