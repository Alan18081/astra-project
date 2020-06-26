package com.alex.astraproject.companiesservice.domain.position.impl;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.clients.EmployeeQueryClient;
import com.alex.astraproject.companiesservice.clients.PositionQueryClient;
import com.alex.astraproject.companiesservice.domain.company.CompanyEventEntity;
import com.alex.astraproject.companiesservice.domain.position.PositionEventEntity;
import com.alex.astraproject.companiesservice.domain.position.PositionEventsRepository;
import com.alex.astraproject.companiesservice.domain.position.PositionService;
import com.alex.astraproject.companiesservice.domain.position.commands.CreatePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.DeletePositionCommand;
import com.alex.astraproject.companiesservice.domain.position.commands.UpdatePositionCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Position;
import com.alex.astraproject.shared.eventTypes.PositionEventType;
import com.alex.astraproject.shared.exceptions.common.NotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class PositionServiceImpl implements PositionService {

  @Autowired
  private PositionEventsRepository positionEventsRepository;

  @Autowired
  private CompanyQueryClient companyQueryClient;

  @Autowired
  private PositionQueryClient positionQueryClient;

  @Override
  public Mono<PositionEventEntity> createPositionCommand(CreatePositionCommand command) {
    return companyQueryClient.findCompanyById(command.getCompanyId())
      .switchIfEmpty(Mono.error(new NotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
      .flatMap(company -> {
        String entityId = UUID.randomUUID().toString();
        PositionEventEntity event = PositionEventEntity.builder()
          .id(null)
          .positionId(entityId)
          .type(PositionEventType.CREATED)
          .data(command)
          .revision(1)
          .build();
        return positionEventsRepository.save(event);
      });
  }

  @Override
  public Mono<PositionEventEntity> updatePositionCommand(UpdatePositionCommand command) {
    return positionQueryClient.findPositionById(command.getPositionId())
      .switchIfEmpty(Mono.error(new NotFoundException(Errors.POSITION_NOT_FOUND_BY_ID)))
      .flatMap(position -> buildEvent(position, PositionEventType.UPDATED, command));
  }

  @Override
  public Mono<PositionEventEntity> deletePositionCommand(DeletePositionCommand command) {
    System.out.println(command);
    return positionQueryClient.findPositionById(command.getPositionId())
      .switchIfEmpty(Mono.error(new NotFoundException(Errors.POSITION_NOT_FOUND_BY_ID)))
      .flatMap(position -> {
        System.out.println("Some event: " + position);
        return buildEvent(position, PositionEventType.DELETED, command);
      });
  }

  private Mono<PositionEventEntity> buildEvent(Position position, String eventType, Object command) {
    return positionEventsRepository.findFirstByPositionIdOrderByRevisionDesc(position.getId())
      .flatMap(positionEventEntity -> {
        PositionEventEntity event = PositionEventEntity.builder()
          .id(null)
          .positionId(position.getId())
          .type(eventType)
          .data(command)
          .revision(positionEventEntity.getRevision() + 1)
          .build();
        return positionEventsRepository.save(event);
      });
  }

  @Override
  public Flux<PositionEventEntity> getEvents(GetEventsDto dto) {
    return positionEventsRepository.findManyEvents(dto);
  }
}
