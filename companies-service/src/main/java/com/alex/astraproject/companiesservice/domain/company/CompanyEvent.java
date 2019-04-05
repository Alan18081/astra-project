package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.shared.DomainEvent;
import com.alex.astraproject.shared.entities.Company;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class CompanyEvent extends DomainEvent<Company, Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private long aggregateId;

    @Column
    private Date creationDate;

    @Type(type = "jsonb")
    private String data;

}
