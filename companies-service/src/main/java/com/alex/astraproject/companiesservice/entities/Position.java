package com.alex.astraproject.companiesservice.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
public class Position {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

    @Column
    private double averageSalary;

    @Column
    private int quantity;

    @OneToMany(targetEntity = Employee.class)
    private Set<Employee> employees;


}
