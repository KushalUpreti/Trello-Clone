package com.cotiviti.socket.controller;

import com.cotiviti.socket.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class MessageController {

    private SimpMessagingTemplate template;

    @Autowired
    public MessageController(SimpMessagingTemplate template) {
        this.template = template;
    }

    private Map<String, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

    @MessageMapping("/broadcast/{roomName}")
    public void broadcastMessageToRoom(@DestinationVariable String roomName, Message message) {
        String returnMessage = message.getMessage() + "-" + message.getUserId() + "-" + roomName;
        this.template.convertAndSend("/topic/room/"+ roomName, returnMessage);
    }

    @MessageMapping("/conversation/{roomName}")
    @SendTo("/topic/messages")
    public String sendMessage(@DestinationVariable String roomName, String message) throws Exception {
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return message + "-" + time + "-" + roomName;
    }

    @MessageMapping("/board/{roomName}")
    public void broadcastMessage(@DestinationVariable String roomName, Message message) {
        Set<WebSocketSession> sessions = roomSessions.getOrDefault(roomName, Collections.emptySet());
        sessions.forEach(session -> {
            if (!session.getId().equals(message.getUserId())) {
                try {
                    session.sendMessage(new TextMessage(message.getMessage()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
