package com.alex.astraproject.projectsservice.domain.sprint;

import com.alex.astraproject.shared.BaseEventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "projectEvent")
@Getter
@ToString
@NoArgsConstructor
public class SprintEventEntity extends BaseEventEntity {

  @Id
  @Field
  private String id;

  @Field
  private String sprintId;

	@Builder
	public SprintEventEntity(String id, String sprintId, Object data, long revision, long timestamp, String type) {
		super(data, revision, timestamp, type);
		this.id = id;
		this.sprintId = sprintId;
	}

}
