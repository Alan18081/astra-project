package com.alex.astraproject.shared.events;

import com.alex.astraproject.companiesservice.domain.EventType;

import java.util.UUID;

public class CompanyEvent extends DomainEvent<CompanyEntity, Long> {

    private CompanyEntity subject;
    private EventType eventType;

    public CompanyEvent() {
        this.setId((long) UUID.randomUUID().hashCode());
    }

    public CompanyEvent(CompanyEntity subject, EventType eventType) {
        this();
        this.subject = subject;
        this.eventType = eventType;
    }

    @Override
    public CompanyEntity getSubject() {
        return subject;
    }

    @Override
    public void setSubject(CompanyEntity subject) {
        this.subject = subject;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }

    @Override
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }
}
