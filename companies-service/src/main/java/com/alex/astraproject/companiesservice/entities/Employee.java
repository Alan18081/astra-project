package com.alex.astraproject.companiesservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@JsonIgnoreProperties({ "password" })
public class Employee {

    @Id
    @GeneratedValue
    private long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String password;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "book_id")
    private Company company;

}
