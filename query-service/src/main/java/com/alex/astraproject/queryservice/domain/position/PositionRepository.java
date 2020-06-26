package com.alex.astraproject.queryservice.domain.position;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface PositionRepository extends PagingAndSortingRepository<PositionEntity, String> {

	Optional<PositionEntity> findFirstByName(String name);

}
