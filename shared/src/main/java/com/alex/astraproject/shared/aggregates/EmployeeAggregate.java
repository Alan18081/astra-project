package com.alex.astraproject.shared.aggregates;

import com.alex.astraproject.shared.Aggregate;
import com.alex.astraproject.shared.DomainEvent;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class EmployeeAggregate implements Aggregate<Long> {

    private UUID id;

    private String email;

    private String password;

    private BigDecimal salary;

    private Date createdAt;

    @Override
    public void initialize() {
        this.id = UUID.randomUUID();
    }

    public void replay(List<DomainEvent<Long>> domainEvents) {
    }



}
