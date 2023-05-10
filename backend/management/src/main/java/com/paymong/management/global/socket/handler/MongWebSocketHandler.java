package com.paymong.management.global.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.socket.service.MongSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class MongWebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final MongSocketService mongSocketService;
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        log.info("payload : {}", payload);

        TextMessage initialGreeting = new TextMessage("Welcome to Mong Management Server ~O_O~");
        session.sendMessage(initialGreeting);
    }
}
