package com.alex.astraproject.shared.interfaces;

import com.alex.astraproject.shared.dto.companies.PaginationDto;
import reactor.core.publisher.Flux;

public interface EventsService<T> {

	Flux<T> getEvents(PaginationDto dto);

	Flux<T> getEventsByRevision(PaginationDto dto);

	Flux<T> getEventsByTimestamp(PaginationDto dto);
}
