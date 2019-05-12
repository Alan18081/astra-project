package com.alex.astraproject.companiesservice.domain.position.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeletePositionCommand {

    private String positionId;

}
