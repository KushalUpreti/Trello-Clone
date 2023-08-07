package com.cotiviti.socket.model;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Board {
    private UUID id;

    private String title;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private User creator;
}
