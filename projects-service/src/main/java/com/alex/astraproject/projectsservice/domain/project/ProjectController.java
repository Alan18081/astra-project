package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.projectsservice.domain.project.commands.common.CreateProjectCommand;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
public class ProjectController {

    @PostMapping
    public Mono<Void> createOne(@RequestBody @Valid CreateProjectCommand command) {
        return Mono.just(null);
    }

}
