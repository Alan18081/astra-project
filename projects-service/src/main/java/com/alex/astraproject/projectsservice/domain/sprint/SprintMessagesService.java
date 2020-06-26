package com.alex.astraproject.projectsservice.domain.sprint;


public interface SprintMessagesService {

	void sendCreatedEvent(SprintEventEntity event);

	void sendDeletedEvent(SprintEventEntity event);

	void sendUpdatedEvent(SprintEventEntity event);

	void sendCompletedEvent(SprintEventEntity event);

	void sendCreatedTaskStatusEvent(SprintEventEntity event);

	void sendDeletedTaskStatusEvent(SprintEventEntity event);

}
