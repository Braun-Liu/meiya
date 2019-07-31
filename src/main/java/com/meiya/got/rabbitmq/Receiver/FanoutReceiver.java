package com.meiya.got.rabbitmq.Receiver;

import com.alibaba.fastjson.JSON;
import com.meiya.got.Config.RabbitmqConfig;
import com.meiya.got.po.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FanoutReceiver {

    private static final Logger log= LoggerFactory.getLogger(FanoutReceiver.class);
    // queues是指要监听的队列的名字
   /* @RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE2)
    public void receiveTopic1(Student student ) {
        System.out.println("-----------------------------------------------------------");

        System.out.println("【我的receiveFanout1监听到消息】" + student);
    }*/


/*    @RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE2)
    public void receiveTopic2() {
        System.out.println("【receiveFanout2监听到消息】" + student);
    }*/
/*
    @RabbitListener(queues =RabbitmqConfig.FANOUT_QUEUE2 )
    public void receiveTopic2(String msg){
        System.out.println("收到新订单请求"+msg);

        return ;

    }*/

   /* @RabbitListener(queues = RabbitmqConfig.FANOUT_QUEUE2,containerFactory = "singleListenerContainer")
    public void consumeMessage(@Payload byte[] message){
        try {
            //TODO：接收String
            String result=new String(message,"UTF-8");
            log.info("接收String消息： {} ",result);
        }catch (Exception e){
            log.error("监听消费消息 发生异常： ",e.fillInStackTrace());
        }
    }*/

}
