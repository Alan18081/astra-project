package com.alex.astraproject.projectsservice.domain.sprint;


public interface SprintMessagesService {

	void sendCreatedEvent(SprintEventEntity event);

	void sendDeletedEvent(SprintEventEntity event);

	void sendUpdatedEvent(SprintEventEntity event);

	void sendCompletedEvent(SprintEventEntity event);

}
