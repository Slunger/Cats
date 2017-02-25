package com.cats.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by andrey on 24.02.17.
 */
@Service
public class MessageService {

    public static final String SUBSCRIPTION_URL = "/topic/message/";

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void send(Integer userId, String msg) {
        final Map<String, Object> map = new HashMap<>();
        map.put("message", msg);
        simpMessagingTemplate.convertAndSend(SUBSCRIPTION_URL + userId, map);
    }
}
