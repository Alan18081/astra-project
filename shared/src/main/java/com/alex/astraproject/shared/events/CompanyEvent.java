package com.alex.astraproject.shared.events;

import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class CompanyEvent {

    private long id;

    private UUID entityId;

    private Date creationDate;

    private CompanyEventType type;

    private Object data;

    public CompanyEvent(CompanyEventType type, UUID entityId, Object data) {
        this.type = type;
        this.entityId = entityId;
        this.data = data;
        this.creationDate = new Date();
    }

}
