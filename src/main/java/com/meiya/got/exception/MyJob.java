package com.meiya.got.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
@Configuration
public class MyJob {

   /* @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendMessage(){
        System.out.println("给服务端发送一个时间戳");
        simpMessagingTemplate.convertAndSend("/server/sendMessageByServer",System.currentTimeMillis());
    }*/
}
