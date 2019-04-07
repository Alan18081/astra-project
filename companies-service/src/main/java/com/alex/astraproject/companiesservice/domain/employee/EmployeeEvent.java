package com.alex.astraproject.companiesservice.domain.employee;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.util.UUID;

@Document
@Getter
public class EmployeeEvent {

    @Id
    @Field
    private String id;

    @Field
    private UUID employeeId;

    @Field
    private String type;

    @Field
    private Object data;

    @Field
    private int revision;

    public EmployeeEvent(UUID employeeId, Object data, String type, int revision) {
        this.employeeId = employeeId;
        this.data = data;
        this.type = type;
        this.revision = revision;
    }
}
