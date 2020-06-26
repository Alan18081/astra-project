package com.alex.astraproject.shared.services;

import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.data.mongodb.core.query.Query;

public interface EventsQueryBuilder {

	Query createQuery(String entityName, GetEventsDto dto);
}
