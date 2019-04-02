package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.CompanyEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompaniesRepository {

    Optional<CompanyEntity> findById(long id);

    void deleteById(long id);

}
