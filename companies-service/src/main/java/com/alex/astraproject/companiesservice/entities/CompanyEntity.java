package com.alex.astraproject.companiesservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String corporateEmail;

    @Column
    private String hashedPassword;

    @OneToMany(targetEntity = EmployeeEntity.class)
    private List<EmployeeEntity> employees;

}
