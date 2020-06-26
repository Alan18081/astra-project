package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.companiesservice.domain.position.commands.CreatePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.DeletePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.UpdatePositionCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface PositionService extends EventsService<PositionEventEntity> {

    Mono<PositionEventEntity> createPositionCommand(CreatePositionCommand command);

    Mono<PositionEventEntity> updatePositionCommand(UpdatePositionCommand command);

    Mono<PositionEventEntity> deletePositionCommand(DeletePositionCommand command);

}
