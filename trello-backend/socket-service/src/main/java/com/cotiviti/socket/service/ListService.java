package com.cotiviti.socket.service;

import com.cotiviti.socket.model.DragEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ListService {
    private final RestTemplate restTemplate;

    public void updateCardOrder(DragEvent dragEvent){
        restTemplate.
                postForObject("http://LIST-SERVICE/update-card-order", dragEvent, String.class);
    }
}
