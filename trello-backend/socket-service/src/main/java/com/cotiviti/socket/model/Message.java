package com.cotiviti.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Message extends User{
    private String message;

    Message(String message, long userId, String email,String username){
        super(userId,email,username);
        this.message = message;
    }
}
