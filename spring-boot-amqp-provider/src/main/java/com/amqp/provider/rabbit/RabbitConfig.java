/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.amqp.provider.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mjj
 * @version Id: RabbitConfig.java, v 0.1 2019年12月19日 14:33 mjj Exp $
 */
@Configuration
public class RabbitConfig {
    @Value("${exchange}")
    String exchange;
    @Value("${queue}")
    String queue;
    @Value("${key}")
    String key;

    /**
     * 路由
     *
     * @return
     */
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    /**
     * 队列
     *
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue(queue);
    }

    /**
     * 绑定
     *
     * @return
     */
    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with(key);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate();
        return template;
    }
}