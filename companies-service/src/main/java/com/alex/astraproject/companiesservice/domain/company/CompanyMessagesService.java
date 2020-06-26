package com.alex.astraproject.companiesservice.domain.company;

public interface CompanyMessagesService {
	void sendCreatedEvent(CompanyEventEntity event);

	void sendDeletedEvent(CompanyEventEntity event);

	void sendUpdatedEvent(CompanyEventEntity event);

}
