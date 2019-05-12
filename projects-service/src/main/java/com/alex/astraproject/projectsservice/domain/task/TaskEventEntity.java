package com.alex.astraproject.projectsservice.domain.task;

import com.alex.astraproject.shared.BaseEventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document(collection = "projectEvent")
@Getter
@ToString
@NoArgsConstructor
public class TaskEventEntity extends BaseEventEntity {

  @Id
  @Field
  private String id;

  @Field
  private String taskId;

	@Builder
	public TaskEventEntity(String id, String taskId, Object data, long revision, String type) {
		super(data, revision, new Date().getTime(), type);
		this.id = id;
		this.taskId = taskId;
	}

}
