package com.app.Todo.consumer;


import com.app.Todo.model.AggregateStatus;
import com.app.Todo.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RabbitMQConsumer {

    @Autowired
    private TodoService todoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consumeJsonMessage(List<AggregateStatus> aggregateStatuses){
        LOGGER.info(String.format("Received from queue -> %s", aggregateStatuses.toString()));
        todoService.saveStatusItems(aggregateStatuses);
    }

}
