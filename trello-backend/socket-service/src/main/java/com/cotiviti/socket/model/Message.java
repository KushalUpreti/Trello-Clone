package com.cotiviti.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Message {
    private String message;
    private String userId;
}
