package com.cotiviti.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragEvent {
    private String boardId;
    private String eventType;
    private long prevList;
    private long targetList;
    private int prevIndex;
    private int currIndex;
}
