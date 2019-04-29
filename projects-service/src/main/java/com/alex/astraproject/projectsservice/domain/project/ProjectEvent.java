package com.alex.astraproject.projectsservice.domain.project;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.UUID;

@Document
@Getter
public class ProjectEvent {

    @Id
    @Field
    private String id;

    @Field
    private UUID projectId;

    @Field
    private String type;

}
