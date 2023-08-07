package com.cotiviti.board.controller;

import com.cotiviti.board.dto.DragEvent;
import com.cotiviti.board.model.Board;
import com.cotiviti.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/boards/{userId}")
    public ResponseEntity<List<Board>> getAllBoards(@PathVariable long userId) {
        List<Board> boards = boardService.getBoardsByUser(userId);
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/board/{board}")
    public ResponseEntity<Board> getBoard(@PathVariable String board) {
        return new ResponseEntity<>(boardService.getBoardById(UUID.fromString(board)), HttpStatus.OK);
    }

    @PostMapping("/create-board/{user}")
    public ResponseEntity<Board> createBoard(@PathVariable long user, @RequestBody Board board) {
        Board newBoard = boardService.createBoard(user, board);
        return new ResponseEntity<>(newBoard, HttpStatus.OK);
    }

    @PostMapping("/update-title/{board}")
    public ResponseEntity<String> updateBoardTitle(@PathVariable String board, @RequestBody String title) {
        Board updatedBoard = boardService.updateBoardTitle(UUID.fromString(board), title);
        return new ResponseEntity<>(updatedBoard.getTitle(), HttpStatus.OK);
    }

    @PostMapping("/update-list-index")
    public ResponseEntity<String> updateListIndex(@RequestBody DragEvent event) {
        boardService.updateListIndex(event);
        return new ResponseEntity<>("List updated",HttpStatus.OK);
    }
}
