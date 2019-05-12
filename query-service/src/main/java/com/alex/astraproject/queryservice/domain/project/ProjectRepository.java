package com.alex.astraproject.queryservice.domain.project;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProjectRepository extends PagingAndSortingRepository<ProjectEntity, String> {

}
