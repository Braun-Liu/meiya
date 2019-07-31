package com.meiya.got.controller;

import com.meiya.got.po.MsgConnection;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

//@Component
//@RabbitListener(queues = "user.queue")
public class MQReceiver {


    @RabbitHandler
    public void onUserMessage(@Payload MsgConnection msgConnection, Channel channel, @Headers Map<String,Object> headers) throws IOException {

        /*try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        System.out.println("msgconnection的user方法-------------------"+msgConnection.getUser_mid());
        int i=msgConnection.getStore_mid();
        long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ack
        if (i==1){

            channel.basicNack(deliveryTag,false,true);
            //channel.basicReject(deliveryTag,true);
            System.out.println("拒绝了此消息-------------------"+i);
        }
        channel.basicAck(deliveryTag,false);
        System.out.println("receive--11: " + msgConnection.toString());
    }





}
