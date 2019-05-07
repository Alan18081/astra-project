package com.alex.astraproject.companiesservice.domain.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.UUID;

@Document
@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Override
    public String toString() {
        return "EmployeeEventEntity{" +
          "id='" + id + '\'' +
          ", employeeId=" + employeeId +
          ", type='" + type + '\'' +
          ", data=" + data +
          ", revision=" + revision +
          '}';
    }
}
