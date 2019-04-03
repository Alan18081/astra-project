package com.alex.astraproject.shared.entities;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class Position {
    private long id;
    private String name;
    private double averageSalary;
    private int quantity;
    private Set<Employee> employees = new HashSet<>();
}
