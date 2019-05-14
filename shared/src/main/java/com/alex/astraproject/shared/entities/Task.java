package com.alex.astraproject.shared.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {

    private String id;

    private String name;

    private String createdAt;

    private String status;

    private Employee employee;

    private String sprintId;

}
