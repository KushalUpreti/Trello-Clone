package com.cotiviti.board.service;

import com.cotiviti.board.dto.BoardEvent;
import com.cotiviti.board.model.Board;
import com.cotiviti.board.model.Card;
import com.cotiviti.board.model.List;
import com.cotiviti.board.repository.BoardRepository;
import com.cotiviti.board.repository.ListRepository;
import com.cotiviti.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListService {
    //    TODO: Call each service for data
    private final ListRepository listRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    public List createList(String boardId, List list) {
        Board board = boardRepository.findById(UUID.fromString(boardId)).orElseThrow();
        int size = board.getLists().size();
        list.setBoard(board);
        list.setListIndex(size + 1);
        return listRepository.save(list);
    }

    public void moveCard(BoardEvent event) {
        if (event.getPrevListId() == event.getTargetListId()) {
           reorderWithinList(event);
        }else{
            reorderBetweenList(event);
        }
    }

    private void reorderWithinList(BoardEvent event){
        List prevList = listRepository.findById(event.getPrevListId()).orElseThrow();
        java.util.List<Card> cards = prevList.getCards();
        int toIndex = event.getCurrentIndex();
        int fromIndex = event.getPrevIndex();
        if (toIndex >= fromIndex) {
            Collections.rotate(cards.subList(fromIndex, toIndex + 1), -1);
        } else {
            Collections.rotate(cards.subList(toIndex, fromIndex + 1), 1);
        }
        prevList.setCards(reorderedList(cards));
        listRepository.save(prevList);
    }

    private void reorderBetweenList(BoardEvent event){
        List prevList = listRepository.findById(event.getPrevListId()).orElseThrow();
        List currentList = listRepository.findById(event.getTargetListId()).orElseThrow();
        Card removedCard = prevList.getCards().remove(event.getPrevIndex());
        currentList.getCards().add(event.getCurrentIndex(),removedCard);
        prevList.setCards(reorderedList(prevList.getCards()));
        currentList.setCards(reorderedList(currentList.getCards()));
        listRepository.save(prevList);
        listRepository.save(currentList);
    }


    private java.util.List<Card> reorderedList(java.util.List<Card> cards){
        for (Card card : cards) {
            card.setCardIndex(cards.indexOf(card));
        }
        return cards;
    }

}
