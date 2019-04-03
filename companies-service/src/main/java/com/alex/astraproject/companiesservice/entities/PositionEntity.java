package com.alex.astraproject.companiesservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class PositionEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private double averageSalary;

    @Column
    private int quantity;

    @OneToMany(targetEntity = EmployeeEntity.class)
    private Set<EmployeeEntity> employees;

    @ManyToOne(targetEntity = CompanyEntity.class)
    @JoinColumn(name = "company_id")
    private CompanyEntity companyEntity;

}
