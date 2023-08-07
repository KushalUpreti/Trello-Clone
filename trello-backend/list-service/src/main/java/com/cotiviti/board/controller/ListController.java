package com.cotiviti.board.controller;

import com.cotiviti.board.dto.BoardEvent;
import com.cotiviti.board.dto.DragEvent;
import com.cotiviti.board.model.List;
import com.cotiviti.board.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ListController {
    private final ListService listService;

    @PostMapping("/add-list/{boardId}")
    public ResponseEntity<List> addList(@PathVariable String boardId, @RequestBody List list) {
        List newList = listService.createList(boardId, list);
        return new ResponseEntity<>(newList, HttpStatus.OK);
    }

    @PostMapping("/update-card-order")
    public ResponseEntity<String> updateListIndex(@RequestBody DragEvent event) {
        listService.moveCard(event);
        return new ResponseEntity<>("List updated",HttpStatus.OK);
    }
}
