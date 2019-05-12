package com.alex.astraproject.projectsservice.domain.project;


public interface ProjectMessagesService {

	void sendCreatedEvent(ProjectEventEntity event);

	void sendDeletedEvent(ProjectEventEntity event);

	void sendUpdatedEvent(ProjectEventEntity event);

	void sendCompletedEvent(ProjectEventEntity event);

	void sendStoppedEvent(ProjectEventEntity event);

	void sendAddedParticipantEvent(ProjectEventEntity event);

	void sendRemovedParticipantEvent(ProjectEventEntity event);

	void sendChangeEmployeePositionEvent(ProjectEventEntity event);

	void sendAddedPositionEvent(ProjectEventEntity event);

	void sendRemovedPositionEvent(ProjectEventEntity event);

}
