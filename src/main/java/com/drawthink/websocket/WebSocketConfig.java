package com.drawthink.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {
    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {

        /*
        withSockJS()当客户端不支持websocket的时候启用sockjs
        setAllowedOrigins("*")允许所有路径的跨域
        addEndpoint("/hello") 设置websocket的注册路径

        第一个路径用于广播式，前端websocket注册/hello路径，然后往controller中的/app/welcome路径发消息，然后经过action处理的消息将被发送到@SendTO注解中的路径
        ，前提是，用户需要在前端订阅这个路径(/topic/getResponse)。

        第二个路径用于点对点式，前端websocket注册/cheat路径，然后往controller中的/app/cheat路径发消息，经过action处理之后发送给/oneToOne/message
        ，用户需要在前端监听/user/oneToOne/message
         */
        stompEndpointRegistry.addEndpoint("/hello").setAllowedOrigins("*").withSockJS();
        stompEndpointRegistry.addEndpoint("/cheat").setAllowedOrigins("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /*
         enable a simple memory-based message broker to carry the greeting messages back to the client on destinations prefixed with "/topic".

         向以/topic 和 /user为前缀的客户端订阅路径发送消息
         */
        registry.enableSimpleBroker("/topic","/oneToOne");

        /*
        给@MessageMapping注解设置的路径设置/app前缀
         */
        registry.setApplicationDestinationPrefixes("/app/");

        /*
        点对点消息的前缀
         */
        registry.setUserDestinationPrefix("/user/");
    }
}
