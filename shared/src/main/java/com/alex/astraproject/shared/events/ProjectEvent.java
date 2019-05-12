package com.alex.astraproject.shared.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProjectEvent extends BaseEvent {

    private String projectId;


}

