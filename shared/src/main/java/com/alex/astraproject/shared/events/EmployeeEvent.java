package com.alex.astraproject.shared.events;

import com.alex.astraproject.shared.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent {

    private String id;

    private String employeeId;

    private String type;

    private Map<String, Object> data;

    private int revision;
}
