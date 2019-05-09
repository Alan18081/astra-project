package com.alex.astraproject.projectsservice.clients;

import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.entities.Project;
import reactor.core.publisher.Mono;

public interface ProjectClient {

	Mono<Project> findProjectById(String id);

	Mono<Project> findProjectById(String id, boolean includeEmployees);

}
