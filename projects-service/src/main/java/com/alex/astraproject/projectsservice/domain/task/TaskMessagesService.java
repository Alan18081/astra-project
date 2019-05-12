package com.alex.astraproject.projectsservice.domain.task;


import com.alex.astraproject.projectsservice.domain.project.ProjectEventEntity;

public interface TaskMessagesService {

	void sendCreatedEvent(TaskEventEntity event);

	void sendDeletedEvent(TaskEventEntity event);

	void sendUpdatedEvent(TaskEventEntity event);

	void sendChangedEmployeeEvent(TaskEventEntity event);

}
