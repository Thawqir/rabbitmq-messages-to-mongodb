package com.example.messagestomongodb.rabbit;

import com.example.messagestomongodb.config.RabbitmqConfig;
import com.example.messagestomongodb.csvparse.CSVParse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class Sender implements CommandLineRunner{

    private final RabbitTemplate rabbitTemplate;
    private final CSVParse csvParse;

    private static final Logger log = LoggerFactory.getLogger(Sender.class);

    @Override
    public void run(String... args) throws Exception {

        log.info("sending message...");
        csvParse.parseCSVFileIntoList()
                                .forEach(message1 ->
                                        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE_NAME,"#",message1));

//        CovidCases cases = new CovidCases(1,"Continent","Country","22/09/2022",1);
//        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE_NAME,"#",cases);
        log.info("message sent...");
    }

}
