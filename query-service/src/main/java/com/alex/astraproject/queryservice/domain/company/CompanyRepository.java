package com.alex.astraproject.queryservice.domain.company;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<CompanyEntity, UUID> {

	Optional<CompanyEntity> findByEmail(String email);

}
