/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.amqp.provider.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author mjj
 * @version Id: RabbitService.java, v 0.1 2019年12月20日 11:21 mjj Exp $
 */
@Component
public class RabbitService {
    private static final Logger logger = LogManager.getLogger();
    @Value("${exchange}")
    String exchange;
    @Value("${key}")
    String key;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 生产者
     */
    @PostConstruct
    public void provider() throws Exception {
        int i = 1;
        while (true) {
            String msg = "测试-" + i;
            rabbitTemplate.convertAndSend(exchange, key, msg.getBytes());
            i++;
            logger.info(msg);
            Thread.sleep(1000);
        }
    }
}