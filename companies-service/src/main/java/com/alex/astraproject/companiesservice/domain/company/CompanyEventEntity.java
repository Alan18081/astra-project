package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.BaseEventEntity;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document
@Getter
public class CompanyEventEntity extends BaseEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String companyId;

    @Field
    private String type;

    @Builder
    public CompanyEventEntity(String id, String companyId, String type, Object data, long revision, long timestamp) {
        super(data, revision, timestamp);
        this.id = id;
        this.companyId = companyId;
        this.type = type;
    }
}
