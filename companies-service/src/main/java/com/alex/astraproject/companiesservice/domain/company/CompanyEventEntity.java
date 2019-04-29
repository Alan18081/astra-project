package com.alex.astraproject.companiesservice.domain.company;

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
public class CompanyEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private UUID companyId;

    @Field
    private String type;

    @Field
    private Object data;

    @Field
    private int revision;
}
