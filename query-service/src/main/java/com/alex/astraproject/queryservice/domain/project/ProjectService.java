package com.alex.astraproject.queryservice.domain.project;


import com.alex.astraproject.queryservice.domain.employee.dto.FindManyEmployeeThatWorksWithProvidedEmployee;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWhereEmployeeWorkedDto;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWhereTwoEmployeesWorksDto;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWithTextDto;
import com.alex.astraproject.shared.events.ProjectEvent;
import com.alex.astraproject.shared.services.Mutable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService extends Mutable<ProjectEvent> {

	Mono<ProjectEntity> findById(String id);

	Flux<ProjectEntity> findMany();

	Flux<ProjectEntity> findManyProjectsWhereTwoEmployeeWorks(FindManyProjectsWhereTwoEmployeesWorksDto dto);

	Flux<ProjectEntity> findManyProjectsWhereEmployeeWorked(FindManyProjectsWhereEmployeeWorkedDto dto);

	Flux<ProjectEntity> findManyProjectsWithTextDto(FindManyProjectsWithTextDto dto);

}
