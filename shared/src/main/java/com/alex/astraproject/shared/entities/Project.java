package com.alex.astraproject.shared.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    private String id;

    private String name;

    private String description;

    private Set<Employee> participants;

}
