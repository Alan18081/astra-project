package com.alex.astraproject.queryservice.domain.employee;

import com.alex.astraproject.shared.Aggregate;
import com.alex.astraproject.shared.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NodeEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEntity implements Aggregate {

    @Id
    private UUID id;

    @Property
    private String email;

    @Property
    private String password;

    @Property
    private BigDecimal salary;

    @Property
    private Date createdAt;

    @Override
    public void initialize() {
        this.id = UUID.randomUUID();
    }

    public void replay(List<DomainEvent> domainEvents) {
    }
}
