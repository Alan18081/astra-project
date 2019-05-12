package com.alex.astraproject.shared.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEvent extends BaseEvent  {
    private String employeeId;
}
