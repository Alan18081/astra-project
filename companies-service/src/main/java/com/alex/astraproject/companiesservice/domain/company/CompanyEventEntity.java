package com.alex.astraproject.companiesservice.domain.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class CompanyEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String companyId;

    @Field
    private String type;

    @Field
    private Object data;

    @Field
    private int revision;

    @Field
    private long timestamp;
}
