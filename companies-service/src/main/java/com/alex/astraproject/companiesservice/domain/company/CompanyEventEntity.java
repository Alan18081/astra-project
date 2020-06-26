package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.BaseEventEntity;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
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

    @Builder
    public CompanyEventEntity(String id, String companyId, Object data, long revision, long timestamp, String type) {
        super(data, revision, timestamp, type);
        this.id = id;
        this.companyId = companyId;
    }
}
