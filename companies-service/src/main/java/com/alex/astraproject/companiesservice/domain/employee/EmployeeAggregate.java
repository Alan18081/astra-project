package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.shared.Aggregate;
import com.alex.astraproject.shared.DomainEvent;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class EmployeeAggregate implements Aggregate<Long> {

    @Id
    private UUID id;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private BigDecimal salary;

    @Column
    private Date createdAt;

    @Override
    public void initialize() {
        this.id = UUID.randomUUID();
    }

    public void replay(List<DomainEvent<Long>> domainEvents) {
    }



}
