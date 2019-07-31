package com.meiya.got.conponent;

import com.sun.security.auth.UserPrincipal;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Principal;

//@Component
public class WebSocketHandleInterceptor implements ChannelInterceptor {

    /**
     * 绑定user到websocket conn上
     * @param message
     * @param channel
     * @return
     */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String username = accessor.getFirstNativeHeader("username");
            if (StringUtils.isEmpty(username)) {
                return null;
            }
            // 绑定user
            Principal principal = new UserPrincipal(username);
            accessor.setUser(principal);
        }
        return message;
    }
}
