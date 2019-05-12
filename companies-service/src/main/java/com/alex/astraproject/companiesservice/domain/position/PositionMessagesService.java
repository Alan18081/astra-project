package com.alex.astraproject.companiesservice.domain.position;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;

public interface PositionMessagesService {

    void sendCreatedPositionEvent(PositionEventEntity event);

    void sendUpdatedPositionEvent(PositionEventEntity event);

    void sendDeletedPositionEvent(PositionEventEntity event);

}
