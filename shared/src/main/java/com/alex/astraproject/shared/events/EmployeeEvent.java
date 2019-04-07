package com.alex.astraproject.shared.events;

import com.alex.astraproject.shared.DomainEvent;
import lombok.Getter;
import java.util.UUID;

@Getter
public class EmployeeEvent extends DomainEvent<String> {

    private String id;

    private UUID employeeId;

    private String type;

    private Object data;

    private int revision;

    public EmployeeEvent(UUID employeeId, Object data, String type, int revision) {
        super(employeeId);
        this.employeeId = employeeId;
        this.data = data;
        this.type = type;
        this.revision = revision;
    }
}
