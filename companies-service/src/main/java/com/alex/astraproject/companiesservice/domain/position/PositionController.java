package com.alex.astraproject.companiesservice.domain.position;

import com.alex.astraproject.companiesservice.domain.position.commands.CreatePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.DeletePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.UpdatePositionCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/positions")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @Autowired
  private PositionMessagesService positionMessagesService;

  @PatchMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> updateOne(@PathVariable String id, @RequestBody @Valid UpdatePositionCommand command) {
    command.setPositionId(id);
    return positionService
      .updatePositionCommand(command)
      .flatMap(event -> {
        positionMessagesService.sendUpdatedPositionEvent(event);
          return Mono.empty();
      });
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Mono<Void> createOne(@RequestBody @Valid CreatePositionCommand command) {
    return positionService
      .createPositionCommand(command)
      .flatMap(event -> {
          positionMessagesService.sendCreatedPositionEvent(event);
          return Mono.empty();
      });
  }

  @DeleteMapping("{id}")
  @ResponseStatus(HttpStatus.ACCEPTED)
  public Mono<Void> deleteOne(@PathVariable String id) {
    return positionService
      .deletePositionCommand(new DeletePositionCommand(id))
      .flatMap(event -> {
        positionMessagesService.sendDeletedPositionEvent(event);
        return Mono.empty();
      });
  }

  @GetMapping("{id}/events")
  public Flux<PositionEventEntity> findManyEvents(
    @PathVariable String id,
    GetEventsDto dto
  ) {
    dto.setEntityId(id);
    return positionService.getEvents(dto);
  }

}
