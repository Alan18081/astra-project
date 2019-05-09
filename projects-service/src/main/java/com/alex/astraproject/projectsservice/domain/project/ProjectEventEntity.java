package com.alex.astraproject.projectsservice.domain.project;

import com.alex.astraproject.shared.BaseEventEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
@ToString
@NoArgsConstructor
public class ProjectEventEntity extends BaseEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String projectId;

	@Builder
	public ProjectEventEntity(String id, String projectId, Object data, long revision, long timestamp, String type) {
		super(data, revision, timestamp, type);
		this.id = id;
		this.projectId = projectId;
	}

}
