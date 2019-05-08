package com.alex.astraproject.shared.interfaces;

import com.alex.astraproject.shared.dto.common.GetEventsDto;
import reactor.core.publisher.Flux;

public interface EventsRepository<T> {

	Flux<T> findManyEvents(GetEventsDto dto);

}
