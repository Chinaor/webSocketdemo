package com.drawthink.websocket.controller;

import com.drawthink.message.ClientMessage;
import com.drawthink.message.ServerMessage;
import com.drawthink.message.ToUserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {

    /*
    广播
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/getResponse")
    public ServerMessage say(ClientMessage clientMessage){
        System.out.println("clientMessage.getName() = " + clientMessage.getName());
        return new ServerMessage("Welcome , "+clientMessage.getName()+" !");
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    /*
    点对点
     */
    @MessageMapping("/cheat")
    public void cheatTo(ToUserMessage toUserMessage){
        System.out.println("toUserMessage.getMessage() = " + toUserMessage.getMessage());
        System.out.println("toUserMessage.getUserId() = " + toUserMessage.getUserId());
        messagingTemplate.convertAndSendToUser(toUserMessage.getUserId(),"/oneToOne/message",toUserMessage.getMessage());
    }
}
