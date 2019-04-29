package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.clients.CompanyClient;
import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
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

import java.util.UUID;

@Service
public class CompanyService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CompanyEventsRepository companyEventsRepository;

    @Autowired
    private CompanyClient companyClient;

    public Flux<CompanyEventEntity> findManyEventsById(UUID companyId, int revisionFrom) {
        return companyEventsRepository.findAllByCompanyIdAndRevisionGreaterThan(companyId, revisionFrom);
    }

    public Mono<CompanyEventEntity> createCompanyCommand(CreateCompanyCommand command) {
      return companyClient.isCompanyExistsByEmail(command.getEmail())
        .flatMap(isExists -> {
          if(isExists) {
            return Mono.error(new CompanyAlreadyExistsException());
          }
          System.out.println("Retrieved company: ");
          UUID entityId = UUID.randomUUID();
          command.setPassword(bCryptPasswordEncoder.encode(command.getPassword()));
          CompanyEventEntity event = new CompanyEventEntity(null, entityId, CompanyEventType.CREATED, command, 1);
          return companyEventsRepository.save(event);
        });
    }

    public Mono<CompanyEventEntity> updateCompanyCommand(UpdateCompanyCommand command) {
      return companyClient.findCompanyById(command.getCompanyId())
        .flatMap(company -> {
          if(company == null) {
            return Mono.error(new CompanyNotFoundException(Errors.COMPANY_NOT_FOUND_BY_ID));
          }
          CompanyEventEntity event = new CompanyEventEntity(null, company.getId(), CompanyEventType.UPDATED, command, 1);
          return companyEventsRepository.save(event);
        });
    }

    public Mono<CompanyEventEntity> deleteCompanyCommand(DeleteCompanyCommand command) {
      return companyClient.findCompanyById(command.getCompanyId())
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
