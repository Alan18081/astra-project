package com.alex.astraproject.shared.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {

    private String id;

    private String name;

    private String description;

    private Date completeAt;

}
