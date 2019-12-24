/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.amqp.consumer;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author mjj
 * @version Id: TestRabbitProvider.java, v 0.1 2019年12月17日 11:26 mjj Exp $
 */
public class TestRabbitProvider {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 提供者
     */
    @Test
    public void producer() {
        // 连接
        Connection connection = null;
        // 信道
        Channel channel = null;
        try {
            // 创建工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.1.77");
            factory.setPort(5672);
            factory.setUsername("test");
            factory.setPassword("123456");
            factory.setVirtualHost("/test");
            // 创建连接
            connection = factory.newConnection();
            // 创建信道
            channel = connection.createChannel();
            // 创建路由(持久化)
            channel.exchangeDeclare("amqp-exchange-test", BuiltinExchangeType.DIRECT, true);
            int i = 1;
            while (true) {
                try {
                    // 获取消息
                    String msg = "测试" + i;
                    // 发送消息
                    channel.basicPublish("amqp-exchange-test", "123", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
                    logger.info("发送成功，消息内容：" + msg);
                    i++;
                    Thread.sleep(3000);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (channel != null) {
                    channel.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}