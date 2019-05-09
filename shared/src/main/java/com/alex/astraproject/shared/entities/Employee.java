package com.alex.astraproject.shared.entities;

import com.alex.astraproject.shared.statuses.EmployeeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String password;
    private Company company;
    private EmployeeStatus status;
}
