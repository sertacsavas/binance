package com.sertac.binance.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class DefaultHandler extends TextWebSocketHandler {

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws JsonProcessingException {
        System.out.println(message.getPayload());
    }
}