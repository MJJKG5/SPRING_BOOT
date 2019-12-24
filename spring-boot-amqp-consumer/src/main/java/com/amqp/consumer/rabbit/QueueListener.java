/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.amqp.consumer.rabbit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @author mjj
 * @version Id: QueueListener.java, v 0.1 2019年12月20日 11:26 mjj Exp $
 */
@Component
public class QueueListener {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 监听
     *
     * @param bytes 字节
     */
    @RabbitListener(queues = {"amqp-queue-test"})
    public void listener(byte[] bytes) {
        logger.info(new String(bytes, StandardCharsets.UTF_8));
    }
}