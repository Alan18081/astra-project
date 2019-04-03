package com.alex.astraproject.shared.entities;


import lombok.Data;

import java.util.List;

@Data
public class Company {

    private long id;

    private String name;

    private String corporateEmail;

    private List<Employee> employees;

    private String password;

}
