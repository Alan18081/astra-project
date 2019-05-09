package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.shared.BaseEventEntity;
import com.alex.astraproject.shared.eventTypes.EmployeeEventType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Document
@Getter
@ToString
public class EmployeeEventEntity extends BaseEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String employeeId;


    @Builder
    public EmployeeEventEntity(Object data, long revision, String id, String employeeId, String type) {
        super(data, revision, new Date().getTime(), type);
        this.id = id;
        this.employeeId = employeeId;

    }
}
