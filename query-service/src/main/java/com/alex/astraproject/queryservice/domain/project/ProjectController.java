package com.alex.astraproject.queryservice.domain.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    public Flux<ProjectEntity> findMany() {
        return projectService.findMany();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<ProjectEntity>> findOneById(@PathVariable String id) {
        return projectService.findById(id)
          .map(ResponseEntity::ok)
          .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
