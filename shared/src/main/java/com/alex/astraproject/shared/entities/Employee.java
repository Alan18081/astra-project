package com.alex.astraproject.shared.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String password;
    private Company company;
}
