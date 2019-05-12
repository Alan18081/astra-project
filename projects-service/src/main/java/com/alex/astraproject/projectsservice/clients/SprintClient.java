package com.alex.astraproject.projectsservice.clients;

import com.alex.astraproject.shared.entities.Project;
import com.alex.astraproject.shared.entities.Sprint;
import reactor.core.publisher.Mono;

public interface SprintClient {

	Mono<Sprint> findSprintById(String id);

}
