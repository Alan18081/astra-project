package com.alex.astraproject.projectsservice.domain.project;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.UUID;

@Document
@Getter
@Builder
public class ProjectEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String projectId;

    @Field
    private String type;

    @Field
    private Object data;

    @Field
    private long revision;

    @Field
    private long timestamp;

}
