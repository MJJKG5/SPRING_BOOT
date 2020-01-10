/**
 * 代码归 MaJunJie 所有,任何公司和个人不得擅自使用, 我方保留通过法律手段追究责任的权利.
 * Copyright (c) 2019-2020 All Rights Reserved.
 */
package com.java.amqp.rabbit;

import com.rabbitmq.client.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author mjj
 * @version Id: RabbitListener.java, v 0.1 2020年01月08日 3:12 下午 mjj Exp $
 */
@Component
public class AmqpListener {
    private static final Logger logger = LogManager.getLogger();

    /**
     * 监听
     *
     * @param message 消息
     * @param channel 通道
     */
    @RabbitListener(queues = {"amqp-queue-test"})
    public void amqpListener(Message message, Channel channel) throws IOException {
        // 编号
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            // 消息
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
//            // 异常
//            String error = null;
//            error.toString();

            // 消息确认
            // deliveryTag 编号(最大值 ： 9223372036854775807)
            // multiple true：确认小于编号的所有消息(批量)；false：确认该编号的消息
            channel.basicAck(deliveryTag, false);
            logger.info("结果：接收成功 编号：" + deliveryTag + "ack：已确认 消息内容：" + msg);
        } catch (Exception e) {
            // 消息拒收
            // deliveryTag 编号(最大值 ： 9223372036854775807)
            // requeue true：消息存入队列(方便发送给其它订阅者)；false：消息从列队中删除(不会发送给其它订阅者)
//            channel.basicReject(deliveryTag, true);

            // 消息拒收(批量)
            // deliveryTag 编号(最大值 ： 9223372036854775807)
            // multiple true：拒收小于编号的所有消息(批量)；false：拒收该编号的消息
            // requeue true：消息存入队列(方便发送给其它订阅者)；false：消息从列队中删除(不会发送给其它订阅者)
            channel.basicNack(deliveryTag, false, true);
            logger.info("结果：消息拒收 编号：" + deliveryTag + " 拒收原因：消息处理过程中出现异常，已将其放回原有列队中！！！");
            logger.error(e.getMessage(), e);
        }
    }
}