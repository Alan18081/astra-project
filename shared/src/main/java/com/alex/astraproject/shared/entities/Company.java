package com.alex.astraproject.shared.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    private UUID id;

    private String name;

    private String corporateEmail;

    private List<Employee> employees;

    private String password;

}
