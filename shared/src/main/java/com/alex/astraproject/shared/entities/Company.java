package com.alex.astraproject.shared.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private String id;

    private String name;

    private String email;

    private Set<Employee> employees = new HashSet<>();

    private String password;

}
