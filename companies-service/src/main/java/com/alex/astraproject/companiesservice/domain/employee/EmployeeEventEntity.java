package com.alex.astraproject.companiesservice.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeEventEntity {

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
    private long revision;
}
