package com.alex.astraproject.shared.entities;

import lombok.Data;

@Data
public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String password;
    private Company company;
}
