package com.alex.astraproject.companiesservice.domain.company.impl;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.domain.company.CompanyEventEntity;
import com.alex.astraproject.companiesservice.domain.company.CompanyEventsRepository;
import com.alex.astraproject.companiesservice.domain.company.CompanyService;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import com.alex.astraproject.shared.dto.common.GetEventsDto;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.exceptions.companies.CompanyAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

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
        CompanyEventEntity event = CompanyEventEntity.builder()
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
        CompanyEventEntity event = CompanyEventEntity.builder()
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
//
  @Override
  public Flux<CompanyEventEntity> getEvents(GetEventsDto dto) {
//    return companyEventsRepository.findManyEvents(dto);
    return null;
  }

}
