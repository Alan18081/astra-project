package com.alex.astraproject.projectsservice.shared;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;

public abstract class AbstractMessagesService<T> {

	protected Message<T> createMessage(T eventEntity) {
		return MessageBuilder.withPayload(eventEntity).build();
	}

}
