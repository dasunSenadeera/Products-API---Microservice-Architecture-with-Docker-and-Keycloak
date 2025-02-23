package com.shop.productSetupService.configuration;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    // Queues for product events
    @Bean
    public Queue productAddQueue() {
        return new Queue("MESSAGE_QUEUE_PRODUCT_ADD");
    }

    @Bean
    public Queue productUpdateQueue() {
        return new Queue("MESSAGE_QUEUE_PRODUCT_UPDATE");
    }

    @Bean
    public Queue productDeleteQueue() {
        return new Queue("MESSAGE_QUEUE_PRODUCT_DELETE");
    }
}
