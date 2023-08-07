package com.cotiviti.socket.service;

import com.cotiviti.socket.model.DragEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final RestTemplate restTemplate;

    public void updateBoardTitle(String board, String title) {
        restTemplate.
                postForObject("http://BOARD-SERVICE/update-title/" + board, title, String.class);
    }

    public void updateListIndex(DragEvent dragEvent){
        restTemplate.
                postForObject("http://BOARD-SERVICE/update-list-index", dragEvent, String.class);
    }
}
