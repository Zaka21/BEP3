package com.example.ginos.infrastructure.config;

import com.example.ginos.infrastructure.driven.messaging.RabbitMqEventPublisher;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Bean
    public RabbitMqEventPublisher EventPublisher(RabbitTemplate template) {
        return new RabbitMqEventPublisher(template);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(Jackson2JsonMessageConverter converter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory());
        rabbitTemplate.setMessageConverter(converter);

        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter converter(Jackson2ObjectMapperBuilder builder) {
        // We need to configure a message converter to be used by RabbitTemplate.
        // We could use any format, but we'll use JSON, so it is easier to inspect.
        ObjectMapper objectMapper = builder
                .createXmlMapper(false)
                .build();

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        // Set this in order to prevent deserialization using the sender-specific
        // __TYPE-ID__ in the message header.
        converter.setAlwaysConvertToInferredType(true);

        return converter;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(host, port);
    }

}
