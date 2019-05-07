package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.dto.GetEventsByRevisionDto;
import com.alex.astraproject.companiesservice.domain.company.dto.GetEventsByTimestampDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.exceptions.companies.CompanyAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
public class CompanyServiceImpl {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private CompanyEventsRepository companyEventsRepository;

  @Autowired
  private CompanyClient companyClient;

  public Mono<CompanyEventEntity> createCompanyCommand(CreateCompanyCommand command) {
    return companyClient.isCompanyExistsByEmail(command.getEmail())
      .flatMap(isExists -> {
        if (isExists) {
          return Mono.error(new CompanyAlreadyExistsException());
        }
        String entityId = UUID.randomUUID().toString();
        command.setPassword(bCryptPasswordEncoder.encode(command.getPassword()));
        CompanyEventEntity event = new CompanyEventEntity.CompanyEventEntityBuilder()
          .id(null)
          .companyId(entityId)
          .type(CompanyEventType.CREATED)
          .data(command)
          .revision(1)
          .timestamp(new Date().getTime())
          .build();
        return companyEventsRepository.save(event);
      });
  }

  public Mono<CompanyEventEntity> updateCompanyCommand(UpdateCompanyCommand command) {
    return companyClient.findCompanyById(command.getCompanyId())
      .switchIfEmpty(Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
      .flatMap(company -> buildEvent(company, CompanyEventType.UPDATED, command));
  }

  public Mono<CompanyEventEntity> deleteCompanyCommand(DeleteCompanyCommand command) {
    return companyClient.findCompanyById(command.getCompanyId())
      .switchIfEmpty(Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID)))
      .flatMap(company -> buildEvent(company, CompanyEventType.DELETED, command));

  }

  private Mono<CompanyEventEntity> buildEvent(Company company, String eventType, Object command) {
    return companyEventsRepository.findFirstByCompanyIdOrderByRevisionDesc(company.getId())
      .flatMap(companyEventEntity -> {
        CompanyEventEntity event = new CompanyEventEntity.CompanyEventEntityBuilder()
          .id(null)
          .companyId(company.getId())
          .type(eventType)
          .data(command)
          .revision(companyEventEntity.getRevision() + 1)
          .timestamp(new Date().getTime())
          .build();
        return companyEventsRepository.save(event);
      });
  }

//  @Override
  public Flux<CompanyEventEntity> getEventsByRevision(GetEventsByRevisionDto dto) {
    Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
    String id = dto.getId();
    Long revisionFrom = dto.getRevisionFrom();
    Long revisionTo = dto.getRevisionTo();

//
//    if(revisionFrom == null || revisionTo == null) {
//      throw new
//    }

    System.out.println(dto.getRevisionFrom());
    System.out.println(dto.getRevisionTo());
    return companyEventsRepository.findAllEvents(revisionFrom, revisionTo);
//    return companyEventsRepository.findManyEvents(id, revisionFrom, revisionTo);
//      .findAllByCompanyIdAndRevisionGreaterThanAndRevisionLessThan(
//        dto.getId(), dto.getRevisionFrom(), dto.getRevisionTo(), pageable
//      );
  }

//  @Override
//  public Flux<CompanyEventEntity> getEventsByTimestamp(GetEventsByTimestampDto dto) {
//    Pageable pageable = PageRequest.of(dto.getPage(), dto.getLimit());
//    String id = dto.getId();
//    long timestampFrom = dto.getTimestampFrom();
//    long timestampTo = dto.getTimestampTo();
//
//    if (Objects.nonNull(dto.getTimestampFrom()) && Objects.nonNull(dto.getTimestampTo())) {
//      return companyEventsRepository
//        .findAllByCompanyIdAndTimestampGreaterThanAndTimestampLessThan(
//          id, timestampFrom, timestampTo, pageable
//        );
//    }
//
//    if (Objects.nonNull(dto.getTimestampFrom())) {
//      return companyEventsRepository
//        .findAllByCompanyIdAndRevisionGreaterThan(id, timestampFrom, pageable);
//    }
//
//    if (Objects.nonNull(dto.getTimestampTo())) {
//      return companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(
//        id,
//        timestampTo,
//        pageable
//      );
//    }
//
//    return companyEventsRepository.findAllByCompanyId(id, pageable);
//  }
}
