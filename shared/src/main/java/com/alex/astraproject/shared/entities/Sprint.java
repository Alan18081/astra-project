package com.alex.astraproject.shared.entities;


import com.alex.astraproject.shared.statuses.SprintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {

    private String id;

    private String name;

    private String description;

    private Date completeAt;

    private Set<Status> taskStatuses;

    private SprintStatus status;

    private String projectId;

}
