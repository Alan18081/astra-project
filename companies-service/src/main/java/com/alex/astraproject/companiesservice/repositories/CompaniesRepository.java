package com.alex.astraproject.companiesservice.repositories;

import com.alex.astraproject.companiesservice.entities.Company;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompaniesRepository {

    Optional<Company> findById(long id);

    void deleteById(long id);

}
