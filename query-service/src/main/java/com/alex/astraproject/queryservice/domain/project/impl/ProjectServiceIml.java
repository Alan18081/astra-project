package com.alex.astraproject.queryservice.domain.project.impl;

import com.alex.astraproject.queryservice.domain.project.ProjectEntity;
import com.alex.astraproject.queryservice.domain.project.ProjectRepository;
import com.alex.astraproject.queryservice.domain.project.ProjectService;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWhereEmployeeWorkedDto;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWhereTwoEmployeesWorksDto;
import com.alex.astraproject.queryservice.domain.project.dto.FindManyProjectsWithTextDto;
import com.alex.astraproject.shared.events.ProjectEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceIml implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Flux<ProjectEntity> findMany() {
        return Flux.fromIterable(projectRepository.findAll());
    }

    @Override
    public Flux<ProjectEntity> findManyProjectsWhereTwoEmployeeWorks(FindManyProjectsWhereTwoEmployeesWorksDto dto) {
        return null;
    }

    @Override
    public Flux<ProjectEntity> findManyProjectsWhereEmployeeWorked(FindManyProjectsWhereEmployeeWorkedDto dto) {
        return null;
    }

    @Override
    public Flux<ProjectEntity> findManyProjectsWithTextDto(FindManyProjectsWithTextDto dto) {
        return null;
    }

    @Override
    public void createOne(ProjectEvent event) {
        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.applyEvent(event);
        projectRepository.save(projectEntity);
    }

    @Override
    public void updateById(ProjectEvent event) {
       applyEvent(event);
    }

    @Override
    public Mono<ProjectEntity> findById(String id) {
        return Mono.justOrEmpty(projectRepository.findById(id));
    }

    private void applyEvent(ProjectEvent event) {
        Mono<ProjectEntity> projectEntityMono = Mono.justOrEmpty(projectRepository.findById(event.getProjectId()));
        projectEntityMono.subscribe(projectEntity -> {
            projectEntity.applyEvent(event);
            projectRepository.save(projectEntity);
        });
    }
}
