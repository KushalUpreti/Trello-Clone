package com.cotiviti.socket.controller;

import com.cotiviti.socket.model.DragEvent;
import com.cotiviti.socket.model.Message;
import com.cotiviti.socket.service.BoardService;
import com.cotiviti.socket.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final SimpMessagingTemplate template;
    private final BoardService boardService;
    private final ListService listService;

    @MessageMapping("/board/update-board-title/{boardId}")
    public void updateBoardTitle(@DestinationVariable String boardId, Message message) {
        boardService.updateBoardTitle(boardId, message.getMessage());
        this.template.convertAndSend("/topic/board/" + boardId + "/update-board-title", message);
    }

    @MessageMapping("/board/update-list-order/{boardId}")
    public void updateListOrder(@DestinationVariable String boardId, DragEvent dragEvent) {
        boardService.updateListIndex(dragEvent);
        this.template.convertAndSend("/topic/board/" + boardId + "/update-list-order", dragEvent);
    }

    @MessageMapping("/board/update-card-order/{boardId}")
    public void updateCardOrder(@DestinationVariable String boardId, DragEvent dragEvent) {
//        listService.updateCardOrder(dragEvent);
        this.template.convertAndSend("/topic/board/" + boardId + "/update-card-order", dragEvent);
    }



}
