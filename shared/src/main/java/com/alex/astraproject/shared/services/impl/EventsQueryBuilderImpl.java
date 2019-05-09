package com.alex.astraproject.shared.services.impl;

import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.services.EventsQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class EventsQueryBuilderImpl implements EventsQueryBuilder {

	@Override
	public Query createQuery(String entityName, GetEventsDto dto) {
		Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
		final Query query = new Query();
		query.addCriteria(Criteria.where(entityName).is(dto.getEntityId()));
		if(dto.getRevisionFrom() != null || dto.getRevisionTo() != null) {
			query.addCriteria(createCriteria("revision", dto.getRevisionFrom(), dto.getRevisionTo()));
		}

		if(dto.getTimestampFrom() != null || dto.getTimestampTo() != null) {
			query.addCriteria(createCriteria("timestamp", dto.getTimestampFrom(), dto.getTimestampTo()));
		}
		return query.with(pageable);
	}

	private Criteria createCriteria(String name, Long valueFrom, Long valueTo) {
		Criteria criteria = Criteria.where(name);
		if(valueFrom != null) {
			criteria = criteria.gt(valueFrom);
		}

		if(valueTo != null) {
			criteria = criteria.lt(valueTo);
		}

		return criteria;
	}
}
