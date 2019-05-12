package com.alex.astraproject.companiesservice.clients;

import com.alex.astraproject.shared.entities.Employee;
import com.alex.astraproject.shared.entities.Position;
import reactor.core.publisher.Mono;

public interface PositionQueryClient {

	Mono<Position> findPositionById(String id);

}
