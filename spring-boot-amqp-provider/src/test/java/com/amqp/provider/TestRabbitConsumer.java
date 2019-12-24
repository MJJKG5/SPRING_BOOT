/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.amqp.provider;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

/**
 * @author mjj
 * @version Id: TestRabbitConsumer.java, v 0.1 2019年12月17日 11:03 mjj Exp $
 */
public class TestRabbitConsumer {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 消费者
     */
    @Test
    public void consumer() throws Exception {
        // 创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);
        factory.setUsername("test");
        factory.setPassword("123456");
        factory.setVirtualHost("/test");
        // 创建连接
        Connection connection = factory.newConnection();
        // 创建信道
        Channel channel = connection.createChannel();
        // 创建路由(持久化)
        channel.exchangeDeclare("amqp-exchange-test", BuiltinExchangeType.DIRECT, true);
        // 创建队列(持久化)
        channel.queueDeclare("amqp-queue-test", true, false, false, new HashMap<>());
        // 绑定列队
        channel.queueBind("amqp-queue-test", "amqp-exchange-test", "123");
        // 监听列队(ack：)
        channel.basicConsume("amqp-queue-test", false, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    logger.info("接收成功，消息内容：" + new String(body) + " consumerTag：" + consumerTag);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        Thread.sleep(1000 * 60 * 60);
    }
}