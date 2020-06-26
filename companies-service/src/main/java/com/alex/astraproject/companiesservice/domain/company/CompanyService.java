package com.alex.astraproject.companiesservice.domain.company;

import com.alex.astraproject.companiesservice.domain.company.commands.CreateCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.DeleteCompanyCommand;
import com.alex.astraproject.companiesservice.domain.company.commands.UpdateCompanyCommand;
import com.alex.astraproject.shared.interfaces.EventsService;
import reactor.core.publisher.Mono;

public interface CompanyService extends EventsService<CompanyEventEntity> {

	Mono<CompanyEventEntity> createCompanyCommand(CreateCompanyCommand command);

	Mono<CompanyEventEntity> updateCompanyCommand(UpdateCompanyCommand command);

	Mono<CompanyEventEntity> deleteCompanyCommand(DeleteCompanyCommand command);

}
