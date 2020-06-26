package com.alex.astraproject.shared;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public abstract class DomainEvent<ID> {
    private ID id;
    private UUID aggregateId;
    private long timestamp;

    protected DomainEvent(UUID aggregateId) {
        this.aggregateId = aggregateId;
        this.timestamp = new Date().getTime();
    }
}
