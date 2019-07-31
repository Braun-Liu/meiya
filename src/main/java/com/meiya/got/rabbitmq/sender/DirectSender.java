package com.meiya.got.rabbitmq.sender;

import com.meiya.got.Config.RabbitmqConfig;
import com.meiya.got.po.MsgConnection;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DirectSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void  sendMsgToUser(MsgConnection msgConnection){
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.USER_EXCHANGE,"store.user",msgConnection);
    }

}
