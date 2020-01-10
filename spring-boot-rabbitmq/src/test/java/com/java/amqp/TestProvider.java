/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.java.amqp;

import com.rabbitmq.client.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

/**
 * @author mjj
 * @version Id: TestProvider.java, v 0.1 2020年01月09日 4:50 下午 mjj Exp $
 */
public class TestProvider {
    private static final Logger logger = LogManager.getLogger();
    private static final String key = "123";
    private static final String exchange = "amqp-exchange-test";

    /**
     * 提供者
     */
    @Test
    public void provider() {
        // 连接
        Connection connection = null;
        // 信道
        Channel channel = null;
        try {
            // 创建工厂
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("test");
            factory.setPassword("123456");
            factory.setVirtualHost("/test");
            // 创建连接
            connection = factory.newConnection();
            // 创建信道
            channel = connection.createChannel();
            // 创建路由(持久化)
            channel.exchangeDeclare(exchange, BuiltinExchangeType.DIRECT, true);
            int i = 1;
            while (true) {
                try {
                    // 消息
                    String msg = "测试" + i;
                    // 推送消息
                    channel.basicPublish(exchange, key, MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
                    logger.info("推送成功，消息内容：" + msg);
                    i++;
                    Thread.sleep(1000);
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