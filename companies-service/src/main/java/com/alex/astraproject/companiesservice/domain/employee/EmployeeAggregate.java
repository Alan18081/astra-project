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
public class EmployeeAggregate implements Aggregate {

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
    public void initialize(UUID id) {
        this.id = id;
    }

    public void replay(List<DomainEvent<Long>> domainEvents) {
    }



}
