package com.paymong.management.global.socket.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.management.global.socket.dto.MongSocketDto;
import com.paymong.management.global.socket.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

    private final WebSocketService mongSocketService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        log.info("체크중... {}", session.getHandshakeHeaders().get("mongid"));
        MongSocketDto socket = MongSocketDto.builder()
                .memberId(Long.parseLong(session.getHandshakeHeaders().get("memberid").get(0)))
                .session(session)
                .build();
        mongSocketService.check(socket);
    }
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();

        log.info("payload : {}", payload);
        if(payload.equals("connect")){
            log.info("연결중... {}", session.getHandshakeHeaders().get("mongid"));
            MongSocketDto socket = MongSocketDto.builder()
                    .memberId(Long.parseLong(session.getHandshakeHeaders().get("memberid").get(0)))
                    .session(session)
                    .build();
            mongSocketService.connect(socket);
        }
//        TextMessage initialGreeting = new TextMessage("Welcome to Mong Management Server ~O_O~");
//        session.sendMessage(initialGreeting);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        log.info("응 연결 끊김~ {}", status.getCode());
        MongSocketDto socket = MongSocketDto.builder()
                .memberId(Long.parseLong(session.getHandshakeHeaders().get("memberid").get(0)))
                .session(session)
                .build();
        mongSocketService.disconnect(socket);
    }
}
