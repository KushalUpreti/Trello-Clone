package com.cotiviti.socket.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DragEvent extends User{
    private String boardId;
    private String eventType;
    private long prevList;
    private long targetList;
    private int prevIndex;
    private int currIndex;
}
