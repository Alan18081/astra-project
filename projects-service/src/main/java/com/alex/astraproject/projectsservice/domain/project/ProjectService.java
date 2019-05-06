package com.alex.astraproject.projectsservice.domain.project.impl;

import com.alex.astraproject.projectsservice.domain.project.commands.common.CreateProjectCommand;
import reactor.core.publisher.Mono;

public interface ProjectService {

	Mono<Void> createOne(CreateProjectCommand command);


}
