package com.cotiviti.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OutputMessage {
    private String from;
    private String message;
    private String time;
}
