package com.alex.astraproject.apigateway.domain.employee;

import com.alex.astraproject.shared.Aggregate;
import com.alex.astraproject.shared.commands.CreateEmployeeCommand;
import com.alex.astraproject.shared.commands.UpdateEmployeeCommand;
import com.alex.astraproject.shared.events.EmployeeEvent;
import com.alex.astraproject.shared.events.EmployeeEventType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Document
@Data
public class EmployeeAggregate implements Aggregate {

    @Id
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private BigDecimal salary;

    @Column
    private Date createdAt;

    @Column
    private int revision;

    @Column
    private boolean deleted;

    @Override
    public void initialize(UUID id) {
        this.id = id;
    }

    public void replay(List<EmployeeEvent> domainEvents) {
        domainEvents.forEach(event -> {
            String eventType = event.getType();

            this.revision = event.getRevision();
            switch (eventType) {
                case EmployeeEventType.CREATED: {
                    CreateEmployeeCommand command = (CreateEmployeeCommand) event.getData();
                    this.email = command.getEmail();
                    this.firstName = command.getFirstName();
                    this.lastName = command.getLastName();
                    this.password = command.getPassword();
                }
                case EmployeeEventType.UPDATED: {
                    UpdateEmployeeCommand command = (UpdateEmployeeCommand) event.getData();
                    if(command.getFirstName() != null) {
                        this.firstName = command.getFirstName();
                    }

                    if(command.getLastName() != null) {
                        this.lastName = command.getLastName();
                    }
                }
                case EmployeeEventType.DELETED: {
                    this.deleted = true;
                }
            }
        });
    }



}
