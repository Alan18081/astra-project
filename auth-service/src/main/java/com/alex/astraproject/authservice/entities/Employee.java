package com.alex.astraproject.authservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({ "password" })
public class Employee {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Company company;

}
