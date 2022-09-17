package com.example.messagestomongodb.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitmqConfig {

    public static final String TOPIC_EXCHANGE_NAME = "covid-exchange";
    public static final String QUEUE_NAME = "cases-entity";

    public static final String DEAD_LETTER_EXCHANGE = "dead-letter-exchange";
    public static final String DEAD_LETTER_QUEUE = "dlq.cases-entity";
    public static final String DEAD_LETTER_ROUTING_KEY = "dlq" + QUEUE_NAME;

    @Bean
    Queue queue(){
        return new Queue(QUEUE_NAME,false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(TOPIC_EXCHANGE_NAME);
    }

    @Bean
    Binding binding(Queue queue, @Qualifier("exchange") TopicExchange topicExchange){
        return BindingBuilder.bind(queue).to(topicExchange).with("#");
    }

    @Bean
    Queue deadLetterQueue(){
        return new Queue(DEAD_LETTER_QUEUE,false);
    }

    @Bean
    TopicExchange deadLetterExchange(){
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    Binding deadLetterBinding(@Qualifier("deadLetterQueue") Queue queue, @Qualifier("deadLetterExchange") TopicExchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY);
    }


    @Bean
    public RabbitTemplate publishingRabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonConverter());
        rabbitTemplate.setChannelTransacted(true);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonConverter(ObjectMapper mapper) {
        return new Jackson2JsonMessageConverter(mapper);
    }

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        return connectionFactory;
    }
}
