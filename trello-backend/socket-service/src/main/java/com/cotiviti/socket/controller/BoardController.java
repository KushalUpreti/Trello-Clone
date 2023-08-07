package com.cotiviti.socket.controller;

import com.cotiviti.socket.model.Message;
import com.cotiviti.socket.service.BoardService;
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

    @MessageMapping("/board/update-board-title/{boardId}")
    public void updateBoardTitle(@DestinationVariable String boardId, Message message) {
        boardService.updateBoardTitle(boardId,message.getMessage());
        this.template.convertAndSend("/topic/board/" + boardId + "/update-board-title", message);
    }

}
