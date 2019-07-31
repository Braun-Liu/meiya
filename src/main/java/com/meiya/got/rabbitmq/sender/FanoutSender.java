package com.meiya.got.rabbitmq.sender;

import com.meiya.got.Config.RabbitmqConfig;
import com.meiya.got.po.MsgConnection;
import com.meiya.got.po.Order;
import com.meiya.got.po.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



    @Component
    public class FanoutSender {



        @Autowired
        private AmqpTemplate rabbitTemplate;

        public void send(Student student) {
            this.rabbitTemplate.convertAndSend(RabbitmqConfig.FANOUT_EXCHANGE, "", student);

        }

        public void send(MsgConnection msgConnection) {
            this.rabbitTemplate.convertAndSend(RabbitmqConfig.FANOUT_EXCHANGE, "", msgConnection);

        }

    }


