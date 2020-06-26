package com.alex.astraproject.queryservice.domain.position;

import com.alex.astraproject.queryservice.shared.entities.BaseEntity;
import com.alex.astraproject.shared.eventTypes.PositionEventType;
import com.alex.astraproject.shared.events.PositionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.beanutils.BeanUtils;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PositionEntity extends BaseEntity {

	@Property
	private String name;

	@Builder
	public PositionEntity(
		String id, Date createdAt, Date deletedAt, long revision,
		String name
	) {
		super(id, createdAt, deletedAt, revision);
		this.name = name;
	}

	public void replay(List<PositionEvent> events) {
		events.forEach(this::applyEvent);
	}

	public void applyEvent(PositionEvent event) {
		switch (event.getType()) {
			case PositionEventType.CREATED: {
				this.id = event.getPositionId();
				this.name = (String) event.getData().get("name");
				this.createdAt = (Date) event.getData().get("createdAt");
				break;
			}
			case PositionEventType.UPDATED: {
				try {
					BeanUtils.populate(this, event.getData());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				break;
			}
			case PositionEventType.DELETED: {
				this.deletedAt = (Date) event.getData().get("deletedAt");
			}
		}
	}

}
