package com.alex.astraproject.shared.entities;

import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = { "name", "averageSalary", "quantity", "employees" })
public class Position {
    private String id;
    private String name;
    private double averageSalary;
    private int quantity;
    private Set<Employee> employees = new HashSet<>();
}
