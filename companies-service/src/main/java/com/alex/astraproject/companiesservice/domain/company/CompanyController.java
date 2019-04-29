package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyMessagesService companyMessagesService;

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> deleteOne(@PathVariable UUID id) {
        return companyService
          .deleteCompanyCommand(new DeleteCompanyCommand(id))
          .flatMap(event -> {
              companyMessagesService.sendDeletedEvent(event);
              return Mono.empty();
          });
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Mono<Void> updateOne(@PathVariable UUID id, @RequestBody @Valid UpdateCompanyCommand command) {
      command.setCompanyId(id);
      return companyService
        .updateCompanyCommand(command)
        .flatMap(event -> {
            companyMessagesService.sendUpdatedEvent(event);
            return Mono.empty();
        });
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> createOne(@RequestBody @Valid CreateCompanyCommand command) {
      return companyService
        .createCompanyCommand(command)
        .flatMap(event -> {
            companyMessagesService.sendCreatedEvent(event);
            return Mono.empty();
        });
    }

    @GetMapping("{id}/events")
    public Flux<CompanyEventEntity> findManyEvents(@PathVariable UUID id, @RequestParam("revisionFrom") int revisionFrom) {
        return companyService.findManyEventsById(id, revisionFrom);
    }

}
