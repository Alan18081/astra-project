package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.clients.CompanyQueryClient;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import com.alex.astraproject.shared.entities.Company;
import com.alex.astraproject.shared.eventTypes.CompanyEventType;
import com.alex.astraproject.shared.exceptions.companies.CompanyAlreadyExistsException;
import com.alex.astraproject.shared.exceptions.companies.CompanyNotFoundException;
import com.alex.astraproject.shared.messages.Errors;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CompanyEventsRepository companyEventsRepository;

    @Autowired
    private CompanyQueryClient companyQueryClient;

    public Flux<CompanyEventEntity> findManyEventsById(UUID companyId, int revisionFrom) {
        return companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(companyId, revisionFrom);
    }

    public Mono<CompanyEventEntity> createCompanyCommand(CreateCompanyCommand command) {
      return Mono.justOrEmpty(companyQueryClient.findOneByEmail(command.getEmail()))
        .filter(Objects::isNull)
        .transform(companyMono -> {
          UUID entityId = UUID.randomUUID();
          command.setPassword(bCryptPasswordEncoder.encode(command.getPassword()));
          CompanyEventEntity event = new CompanyEventEntity(null, entityId, CompanyEventType.CREATED, command, 1);
          return companyEventsRepository.save(event);
        });
    }

    public Mono<CompanyEventEntity> updateCompanyCommand(UpdateCompanyCommand command) {
      Mono<Company> companyMono = Mono.justOrEmpty(companyQueryClient.findOneById(command.getCompanyId().toString()));
      return companyMono
        .flatMap(company -> {
          if(company == null) {
            return Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID));
          }
          CompanyEventEntity event = new CompanyEventEntity(null, company.getId(), CompanyEventType.UPDATED, command, 1);
          return companyEventsRepository.save(event);
        });
    }

    public Mono<CompanyEventEntity> deleteCompanyCommand(DeleteCompanyCommand command) {
      Mono<Company> companyMono = Mono.justOrEmpty(companyQueryClient.findOneById(command.getCompanyId().toString()));
      return companyMono
        .flatMap(company -> {
          if(company == null) {
              return Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID));
          }
          System.out.println("Calling some method");
          return companyEventsRepository.findFirstByCompanyIdOrderByRevisionDesc(company.getId());
        })
        .flatMap(companyEventEntity -> {
          CompanyEventEntity event = new CompanyEventEntity(null, command.getCompanyId(), CompanyEventType.DELETED, null, companyEventEntity.getRevision() + 1);
          System.out.println("Calling some method");
          return companyEventsRepository.save(event);
        });
    }

}
