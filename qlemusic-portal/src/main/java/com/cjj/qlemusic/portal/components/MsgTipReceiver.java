package com.cjj.qlemusic.portal.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 消息提示的接收者
 */
@Component
@RabbitListener(queues = "qlemusic.msg.cancel")
public class MsgTipReceiver {
    private static Logger LOGGER = LoggerFactory.getLogger(MsgTipReceiver.class);

    @RabbitHandler
    public void handle(){
        LOGGER.info("process orderId:{}");
    }
}
