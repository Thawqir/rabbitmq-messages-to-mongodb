package com.example.messagestomongodb.rabbit;

import com.example.messagestomongodb.config.RabbitmqConfig;
import com.example.messagestomongodb.csvparse.CSVParse;
import com.example.messagestomongodb.domain.CovidCases;
import com.example.messagestomongodb.repository.CasesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Sender implements CommandLineRunner{

    private final CasesRepository casesRepository;
    private final RabbitTemplate rabbitTemplate;
    private final CSVParse csvParse;

    @Override
    public void run(String... args) throws Exception {

        log.info("sending message...");
        csvParse.parseCSVFileIntoList()
                .forEach(message1 -> {
                    if (checkForDuplicateRecords(message1.getId(), message1)) {
                        rabbitTemplate.convertAndSend(RabbitmqConfig.DEAD_LETTER_EXCHANGE, RabbitmqConfig.DEAD_LETTER_ROUTING_KEY, message1);
                        log.info("Message sent to dlq with duplicate id: {}",message1.getId());
                    } else {
                        rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE_NAME,"#",message1);
                        log.info("message sent");
                    }
                });
    }

    public boolean checkForDuplicateRecords(Integer id, CovidCases cases){
        Integer existingId = casesRepository.returnIntegerFromDB(id);

        if (existingId.equals(1)){
            log.info("existingid is {} and casesid is {}",existingId,cases.getId());
            return true;
        }
        return false;
    }

}