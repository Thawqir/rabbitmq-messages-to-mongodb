package com.example.messagestomongodb.rabbit;

import com.example.messagestomongodb.config.RabbitmqConfig;
import com.example.messagestomongodb.domain.CovidCases;
import com.example.messagestomongodb.repository.CasesRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableMongoRepositories
public class Receiver {

    private final CasesRepository casesRepository;

    private static final Logger log = LoggerFactory.getLogger(Receiver.class);

    @RabbitListener(queues = RabbitmqConfig.QUEUE_NAME)
    public void consumeMessageToMongoDb(final CovidCases message) {
        casesRepository.save(message);
        log.info("received: " + message.toString());
    }
}
