package com.alex.astraproject.shared.entities;

import com.alex.astraproject.shared.statuses.EmployeeStatus;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Employee {
    @EqualsAndHashCode.Include
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private int age;
    private String password;
    private Company company;
    private EmployeeStatus status;
}
