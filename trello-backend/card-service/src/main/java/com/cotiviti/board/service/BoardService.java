package com.cotiviti.board.service;

import com.cotiviti.board.model.Board;
import com.cotiviti.board.model.User;
import com.cotiviti.board.repository.BoardRepository;
import com.cotiviti.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public List<Board> getBoardsByUser(long userId){
        return boardRepository.findBoardByCreator_Id(userId);
    }

    public Board getBoardById(UUID boardId){
        return boardRepository.findById(boardId).orElseThrow();
    }

    public Board createBoard(long userId, Board board){
        User user = userRepository.findById(userId).orElseThrow();
        board.setCreator(user);
        return boardRepository.save(board);
    }

    public Board updateBoardTitle(UUID boardId,String title){
       Board board = boardRepository.getReferenceById(boardId);
       board.setTitle(title);
       boardRepository.save(board);
       return board;
    }

}
