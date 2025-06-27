package com.neto.posts.postservice.infrastructure.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_POST_PROCESSOR = "post-service.post-processing-result.v1.q";

    public static final String DIRECT_EXCHANGE_NAME = "text-processor.direct-exchange";
    public static final String QUEUE_TEXT_PROCESSOR = "text-processor-service.post-processing.v1.q";
    public static final String DEAD_LETTER_QUEUE_TEXT_PROCESSOR  = "text-processor.queue.dlq";

    public static final String ROUTING_KEY_PROCESS_POST = "post.process";

    // Exchange Direct
    @Bean
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(DIRECT_EXCHANGE_NAME).durable(true).build();
    }

    // Fila Principal com DLQ
    @Bean
    public Queue queueProcessTemperature() {
        final Map<String, Object> args = new HashMap<>();
        args.put("x-dead-letter-exchange", ""); // default exchange
        args.put("x-dead-letter-routing-key", DEAD_LETTER_QUEUE_TEXT_PROCESSOR);
        return QueueBuilder.durable(QUEUE_TEXT_PROCESSOR).withArguments(args).build();
    }

    // Fila DLQ
    @Bean
    public Queue deadLetterQueueProcessTemperature() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE_TEXT_PROCESSOR).build();
    }

    // Binding da fila principal
    @Bean
    public Binding bindingQueueProcessTemperature(final DirectExchange directExchange) {
        return BindingBuilder
                .bind(queueProcessTemperature())
                .to(directExchange)
                .with(ROUTING_KEY_PROCESS_POST);
    }

    // RabbitTemplate com conversor Jackson
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter(final ObjectMapper objectMapper) {
        return new Jackson2JsonMessageConverter(objectMapper);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                         final Jackson2JsonMessageConverter messageConverter) {
        final RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(final ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }
}