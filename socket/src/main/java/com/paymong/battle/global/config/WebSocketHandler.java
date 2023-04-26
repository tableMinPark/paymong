package com.paymong.battle.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paymong.battle.battle.dto.request.BattleMessageReqDto;
import com.paymong.battle.battle.dto.common.BattleRoom;
import com.paymong.battle.battle.service.BattleService;
import com.paymong.battle.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final BattleService battleService;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        BattleMessageReqDto battleMessageReqDto = objectMapper.readValue(payload, BattleMessageReqDto.class);

        try {
            BattleRoom battleRoom = battleService.findRoomById(battleMessageReqDto.getBattleRoomId());
            battleRoom.handlerActions(session, battleMessageReqDto, battleService);
        } catch (NotFoundException e) {
            log.error("Not Found BattleRoom");
            battleService.sendMessage(session, message);
        }
    }
}
