package com.alex.astraproject.companiesservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Company {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private String corporateEmail;

    @OneToMany(targetEntity = Employee.class)
    private List<Employee> employees;

}
