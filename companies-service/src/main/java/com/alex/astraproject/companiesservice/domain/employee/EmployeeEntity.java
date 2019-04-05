package com.alex.astraproject.companiesservice.domain.employee;

import com.alex.astraproject.companiesservice.domain.position.PositionEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties({ "password" })
public class EmployeeEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    private double salary;

    @ManyToOne(targetEntity = PositionEntity.class)
    @JoinColumn(name = "position_id")
    private PositionEntity position;

    @ManyToOne(targetEntity = CompanyEntity.class)
    @JoinColumn(name = "company_id")
    private CompanyEntity company;


}