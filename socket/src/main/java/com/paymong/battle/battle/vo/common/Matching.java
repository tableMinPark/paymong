package com.paymong.battle.battle.vo.common;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

@Data
@Builder
public class Matching {
    private Long characterId;
    private WebSocketSession session;
}
