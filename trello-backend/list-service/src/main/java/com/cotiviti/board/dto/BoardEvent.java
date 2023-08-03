package com.cotiviti.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardEvent {
    private String boardId;
    private String eventType;
    private long prevListId;
    private long targetListId;
    private int prevIndex;
    private int currentIndex;
}
