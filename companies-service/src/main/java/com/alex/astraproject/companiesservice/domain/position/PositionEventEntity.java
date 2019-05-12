package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.shared.BaseEventEntity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;


@Document
@Getter
@ToString
@NoArgsConstructor
@Data
public class PositionEventEntity extends BaseEventEntity {

    @Id
    @Field
    private String id;

    @Field
    private String positionId;


    @Builder
    public PositionEventEntity(Object data, long revision, String id, String positionId, String type) {
        super(data, revision, new Date().getTime(), type);
        this.id = id;
        this.positionId = positionId;

    }
}
