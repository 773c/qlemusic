package com.cjj.qlemusic.portal.components;

import com.cjj.qlemusic.common.constant.QueueConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息提示的生产者
 */
@Component
public class MsgTipSender {
    private static Logger LOGGER = LoggerFactory.getLogger(MsgTipSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMessage(){
        //给延迟队列发送消息
        amqpTemplate.convertAndSend(QueueConstant.QUEUE_TTL_ORDER_CANCEL.getExchange(), QueueConstant.QUEUE_TTL_ORDER_CANCEL.getRouteKey(), "1", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                //给消息设置延迟毫秒值
                message.getMessageProperties().setExpiration(String.valueOf(1000));
                return message;
            }
        });
        LOGGER.info("send msgTil:{}",1);
    }

}
