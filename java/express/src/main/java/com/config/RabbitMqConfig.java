package com.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangguohao
 * @date 2024/10/23 22:21
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("testExchange");
    }

    @Bean
    public Queue testQueue() {
        return new Queue("testQueue");
    }


    @Bean
    public Binding binding(Queue testQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(testQueue).to(directExchange).with("testRoutingKey");
    }

    @Bean
    public Queue importDataQueue() {
        return new Queue("importDataQueue");
    }

    @Bean
    public Binding importData(Queue importDataQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(importDataQueue).to(directExchange).with("importData");
    }
}

